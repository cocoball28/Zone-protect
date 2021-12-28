package org.zone.region.flag.interact.itemframe;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class InteractItemframesListener {
    @Listener
    public void onAttack(DamageEntityEvent event, @Root Player attacker) {

        if (!event.entity().type().equals(EntityTypes.ITEM_FRAME)) {
            return;
        }

        @NotNull Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.entity().position());
        if (opZone.isEmpty()) {
            return;
        }

        Zone zone = opZone.get();
        Group group = zone.getMembers().getGroup(attacker.uniqueId());
        if (group.contains(GroupKeys.INTERACT_ITEMFRAME)) {
            return;
        }
        event.setCancelled(true);
    }

}