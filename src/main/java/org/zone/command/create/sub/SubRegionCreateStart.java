package org.zone.command.create.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.permission.Subject;
import org.zone.ZonePlugin;
import org.zone.command.ZoneArguments;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;
import org.zone.region.regions.Region;
import org.zone.region.regions.type.PointRegion;

public class SubRegionCreateStart {

    public static final class Executor implements CommandExecutor {
        @Override
        public CommandResult execute(CommandContext context) {
            Subject subject = context.subject();
            if (!(subject instanceof ServerPlayer player)) {
                return CommandResult.error(Component.text("Player only command"));
            }
            Zone zone = context.requireOne(ZONE);
            String name = context.requireOne(NAME);
            Region region = new PointRegion(player.world().key(), player.blockPosition().add(0, -1, 0),
                    player.blockPosition());
            ZoneBuilder builder = new ZoneBuilder()
                    .setParent(zone)
                    .setKey(zone.getKey() + "_" + name.toLowerCase().replaceAll(" ", "_"))
                    .setName(name)
                    .setRegion(region)
                    .setContainer(ZonePlugin.getZonesPlugin().getPluginContainer());
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

    public static final Parameter.Value<Zone> ZONE =
            ZoneArguments.createZoneArgument((stream, context) -> stream
                            .filter(zone -> zone.getParentId().isEmpty())
                            .filter(zone -> {
                                Subject subject = context.subject();
                                if (!(subject instanceof Player player)) {
                                    return false;
                                }
                                return zone.getRegion().inRegion(player, zone.getParentId().isEmpty());
                            }))
                    .key("zone")
                    .build();
    public static final Parameter.Value<String> NAME = Parameter.remainingJoinedStrings().key("name").build();

}
