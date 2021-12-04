package org.zone.commands.structure.zone.info.bounds;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.world.Locatable;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.event.listener.PlayerListener;
import org.zone.region.Zone;
import org.zone.region.group.DefaultGroups;
import org.zone.region.group.key.GroupKeys;
import org.zone.region.regions.BoundedRegion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ZoneInfoBoundsShowCommand implements ArgumentCommand {

    ZoneArgument ZONE = new ZoneArgument("zoneId", new ZoneArgument
            .ZoneArgumentPropertiesBuilder()
            .setLevel(GroupKeys.OWNER));

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(
                new ExactArgument("zone"),
                new ExactArgument("info"),
                new ExactArgument("bounds"),
                new ExactArgument("show"),
                ZONE
        );
    }

    @Override
    public Component getDescription() {
        return Component.text("Show the bounds of a specified region");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public CommandResult run(CommandContext context, String... args) throws NotEnoughArgumentsException {
        Subject subject = context.getSource();
        if (!(subject instanceof Viewer viewer && subject instanceof Locatable locatable)) {
            return CommandResult.error(Component.text("Player only command"));
        }

        Zone zone = context.getArgument(this, ZONE);
        if (!(zone.getRegion() instanceof BoundedRegion region)) {
            return CommandResult.error(Component.text("That zones region style is not yet supported"));
        }

        PlayerListener.runOnOutside(region, locatable.blockPosition().y() - 1,
                vector3i -> viewer.sendBlockChange(vector3i, BlockTypes.ORANGE_WOOL.get().defaultState()),
                zone.getParentId().isPresent());
        Sponge
                .server()
                .scheduler()
                .submit(Task
                        .builder()
                        .plugin(ZonePlugin.getZonesPlugin().getPluginContainer())
                        .delay(10, TimeUnit.SECONDS)
                        .execute(() -> PlayerListener
                                .runOnOutside(region, locatable.blockPosition().y() - 1,
                                        viewer::resetBlockChange,
                                        zone
                                                .getParentId()
                                                .isPresent()))
                        .build());
        return CommandResult.success();
    }
}
