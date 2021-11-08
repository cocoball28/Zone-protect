package org.zone.command.create;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.zone.ZonePlugin;
import org.zone.event.listener.PlayerListener;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;
import org.zone.region.regions.BoundedRegion;
import org.zone.region.regions.Region;

import java.util.Optional;

public class RegionCreateEnd {

    public static class Executor implements CommandExecutor {
        @Override
        public CommandResult execute(CommandContext context) {
            Subject subject = context.subject();
            if (!(subject instanceof Player player)) {
                return CommandResult.error(Component.text("Player only command"));
            }
            Optional<ZoneBuilder> opZone = ZonePlugin.getInstance().getMemoryHolder().getZoneBuilder(player.uniqueId());
            if (opZone.isEmpty()) {
                return CommandResult.error(Component.text("A region needs to be started. Use /zone create bounds " +
                        "<name...>"));
            }
            Zone zone = opZone.get().build();
            ZonePlugin.getInstance().getZoneManager().register(zone);
            player.sendMessage(Component.text("Created a new zone of ").append(Component.text(zone.getName()).color(NamedTextColor.AQUA)));
            ZonePlugin.getInstance().getMemoryHolder().unregisterZoneBuilder(player.uniqueId());
            Region region = zone.getRegion();
            if (region instanceof BoundedRegion r) {
                PlayerListener.runOnOutside(r, player.location().blockY() + 3, player::resetBlockChange,
                        zone.getParent().isPresent());
            }

            return CommandResult.success();
        }

    }


}
