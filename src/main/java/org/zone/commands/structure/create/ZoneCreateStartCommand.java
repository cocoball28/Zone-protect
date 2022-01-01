package org.zone.commands.structure.create;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.math.vector.Vector3i;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.simple.StringArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.ZoneBuilder;
import org.zone.region.bounds.BoundedRegion;
import org.zone.region.bounds.ChildRegion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The command for zone bound creation start.
 * <p>Command: "/zone create bounds start 'name'"</p>
 */
public class ZoneCreateStartCommand implements ArgumentCommand {

    private static final RemainingArgument<String> NAME = new RemainingArgument<>(new StringArgument(
            "key"));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("create"),
                             new ExactArgument("bounds"),
                             new ExactArgument("start"),
                             NAME);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Create a zone via walking edge to edge");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_CREATE_BOUNDS.getPermission());
    }

    @Override
    public @NotNull CommandResult run(CommandContext context, String... args) {
        Subject subject = context.getSource();
        if (!(subject instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerCommandError());
        }

        String name = String.join(" ", context.getArgument(this, NAME));
        Vector3i vector3i = player.location().blockPosition();
        BoundedRegion region = new BoundedRegion(new Vector3i(vector3i.x(), 0, vector3i.z()),
                                                 new Vector3i(vector3i.x(), 256, vector3i.z()));

        ChildRegion childRegion = new ChildRegion(Collections.singleton(region));

        ZoneBuilder builder = new ZoneBuilder()
                .setName(name)
                .setContainer(ZonePlugin.getZonesPlugin().getPluginContainer())
                .setKey(name.toLowerCase().replaceAll(" ", "_"))
                .setWorld(player.world())
                .setRegion(childRegion);
        if (ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getZone(builder.getContainer(), builder.getKey())
                .isPresent()) {
            return CommandResult.error(Messages.getDuplicateNameError());
        }
        ZonePlugin
                .getZonesPlugin()
                .getMemoryHolder()
                .registerZoneBuilder(player.uniqueId(), builder);
        player.sendMessage(Messages.getZoneRegionBuilderEnabled());
        return CommandResult.success();
    }

    @Override
    public boolean hasPermission(CommandCause source) {
        if (!(source.subject() instanceof Player)) {
            return false;
        }
        return ArgumentCommand.super.hasPermission(source);
    }
}
