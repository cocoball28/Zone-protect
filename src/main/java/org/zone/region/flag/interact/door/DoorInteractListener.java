package org.zone.region.flag.interact.door;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.tag.BlockTypeTags;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class DoorInteractListener {

    @Listener
    public void onPlayerInteractSecondary(InteractBlockEvent.Secondary event, @Root Player player) {
        BlockSnapshot snapshot = event.block();
        @NotNull Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(player.world(), event.interactionPoint());
        if (opZone.isEmpty()) {
            return;
        }
        Zone zone = opZone.get();
        if (BlockTypeTags.DOORS.get().contains(snapshot.state().type())) {
            DoorInteractionFlag flag = zone.getFlag(FlagTypes.DOOR_INTERACTION).orElse(DoorInteractionFlag.ELSE);
            if (!flag.isEnabled()) {
                return;
            }
            if (player instanceof ServerPlayer sPlayer &&
                    sPlayer.hasPermission(Permissions.BYPASS_DOOR_INTERACTION.getPermission())) {
                return;
            }
            if (zone.getParentId().isPresent() &&
                    zone
                            .getParent()
                            .map(parent -> parent
                                    .getMembers()
                                    .getGroup(player.uniqueId())
                                    .getAllKeys()
                                    .contains(GroupKeys.INTERACT_DOOR))
                            .orElse(false)) {
                return;
            }
            if (!flag.hasPermission(zone, player.uniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
