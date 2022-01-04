package org.zone.region.flag.entity.player.damage.attack;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventContextKeys;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class EntityDamagePlayerListener {
    @Listener
    public void onEntityDamageEvent(DamageEntityEvent event) {

        if (!(event.entity() instanceof Player player)) {
            return;
        }

        Optional<DamageType> optEventContextKeys = event.context().get(EventContextKeys.DAMAGE_TYPE);
        if (optEventContextKeys.isEmpty() || !(optEventContextKeys.get().equals(DamageTypes.ATTACK))) {
            return;
        }

        Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.entity().position());
        if (opZone.isEmpty()) {
            return;
        }

        Zone zone = opZone.get();
        Group group = zone.getMembers().getGroup(player.uniqueId());
        if (group.contains(GroupKeys.ENTITY_DAMAGE_PLAYER)) {
            event.setCancelled(true);
         }
    }
}