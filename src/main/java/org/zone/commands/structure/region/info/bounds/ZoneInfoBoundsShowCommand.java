package org.zone.commands.structure.region.info.bounds;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.world.Locatable;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.event.listener.PlayerListener;
import org.zone.region.Zone;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Used to see the bounds of a zone
 */
public class ZoneInfoBoundsShowCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("info"),
                             ZONE,
                             new ExactArgument("bounds"),
                             new ExactArgument("show"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Show the bounds of a specified region");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public @NotNull CommandResult run(CommandContext context, String... args) {
        Subject subject = context.getSource();
        if (!(subject instanceof Viewer viewer && subject instanceof Locatable locatable)) {
            return CommandResult.error(Messages.setUniversalPlayerOnlyCommandErrorMessage());
        }

        Zone zone = context.getArgument(this, ZONE);
        zone.getRegion().getTrueChildren().forEach(region -> {
            PlayerListener.runOnOutside(region,
                                        locatable.blockPosition().y() - 1,
                                        vector3i -> viewer.sendBlockChange(vector3i,
                                                                           BlockTypes.ORANGE_WOOL
                                                                                   .get()
                                                                                   .defaultState()),
                                        zone.getParentId().isPresent());
            Sponge
                    .server()
                    .scheduler()
                    .submit(Task
                                    .builder()
                                    .plugin(ZonePlugin.getZonesPlugin().getPluginContainer())
                                    .delay(10, TimeUnit.SECONDS)
                                    .execute(() -> PlayerListener.runOnOutside(region,
                                                                               locatable
                                                                                       .blockPosition()
                                                                                       .y() - 1,
                                                                               viewer::resetBlockChange,
                                                                               zone
                                                                                       .getParentId()
                                                                                       .isPresent()))
                                    .build());
        });
        return CommandResult.success();
    }
}
