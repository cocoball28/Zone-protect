package org.zone.commands.structure.create;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.math.vector.Vector3i;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.simple.StringArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.ZoneBuilder;
import org.zone.region.regions.Region;
import org.zone.region.regions.type.PointRegion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneCreateStartCommand implements ArgumentCommand {

    private static final RemainingArgument<String> NAME = new RemainingArgument<>(new StringArgument("key"));

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("create"), new ExactArgument("bounds"), new ExactArgument("start"),
                NAME);
    }

    @Override
    public Component getDescription() {
        return Component.text("Create a zone via walking edge to edge");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_CREATE_BOUNDS.getPermission());
    }

    @Override
    public boolean hasPermission(CommandCause source) {
        if (!(source.subject() instanceof Player)) {
            return false;
        }
        return ArgumentCommand.super.hasPermission(source);
    }

    @Override
    public CommandResult run(CommandContext context, String... args) {
        Subject subject = context.getSource();
        if (!(subject instanceof ServerPlayer player)) {
            return CommandResult.error(Component.text("Player only command"));
        }

        String name = String.join(" ", context.getArgument(this, NAME));
        Vector3i vector3i = player.location().blockPosition();
        Region region = new PointRegion(player.world().key(), new Vector3i(vector3i.x(), 0, vector3i.z()),
                new Vector3i(vector3i.x(), 256, vector3i.z()));

        ZoneBuilder builder = new ZoneBuilder()
                .setName(name)
                .setContainer(ZonePlugin.getZonesPlugin().getPluginContainer())
                .setKey(name.toLowerCase().replaceAll(" ", "_"))
                .setRegion(region);
        if (ZonePlugin.getZonesPlugin().getZoneManager().getZone(builder.getContainer(), builder.getKey()).isPresent()) {
            return CommandResult.error(Component.text("Cannot use that name").color(NamedTextColor.RED));
        }
        ZonePlugin.getZonesPlugin().getMemoryHolder().registerZoneBuilder(player.uniqueId(), builder);
        player.sendMessage(Component
                .text("Region builder mode enabled. Run ")
                .append(Component
                        .text("'/zone create end'")
                        .color(NamedTextColor.AQUA)));
        return CommandResult.success();
    }
}
