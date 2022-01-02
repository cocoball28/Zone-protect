package org.zone.region.flag.player.falldamage;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class PlayerFallDamageListener {
    @Listener
    public void onPlayerFallDamageEvent(CollideBlockEvent.Fall event) {

        if (!(event instanceof Player player)) {
            return;
        }

        if (event.cause().first(DamageSource.class).isPresent()) {
            if (!(event.cause().first(DamageSource.class).get().equals(DamageSources.FALLING))) {
                return;
            }
        }

        Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(((Player) event).world(), ((Player) event).position());
        if (opZone.isEmpty()) {
            return;
        }

        Zone zone = opZone.get();
        Group group = zone.getMembers().getGroup(player.uniqueId());
        if (group.contains(GroupKeys.PLAYER_FALL_DAMAGE)) {
            event.setCancelled(true);
        }
    }
}