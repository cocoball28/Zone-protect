package org.zone.region.flag.entity.player.interact.itemframe;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class ItemFrameInteractionListener {
    @Listener
    public void onAttack(InteractEntityEvent event, @First Player attacker) {
        if (!event.entity().type().equals(EntityTypes.ITEM_FRAME.get())) {
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
        Group group = zone.getMembers().getGroup(attacker.uniqueId());
        if (group.contains(GroupKeys.INTERACT_ITEMFRAME)) {
            return;
        }
        event.setCancelled(true);
    }

}