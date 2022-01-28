package org.zone.region.flag.entity.player.damage.fire;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.service.permission.Subject;
import org.zone.ZonePlugin;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class PlayerFireDamageListener {

    @Listener
    public void onFireDamage(DamageEntityEvent event) {
        if (!(event.entity() instanceof Player player)) {
            return;
        }

        if (player instanceof Subject subject) {
            if (ZonePermissions.BYPASS_DAMAGE_FIRE.hasPermission(subject)) {
                event.setCancelled(true);
                return;
            }
        }

        Optional<DamageType> opDamageType  = event.cause().first(DamageType.class);
        if (opDamageType.isEmpty() || !opDamageType.get().equals(DamageTypes.FIRE.get())) {
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
        if (group.contains(GroupKeys.PLAYER_FIRE_DAMAGE)) {
            event.setCancelled(true);
        }
    }
}
