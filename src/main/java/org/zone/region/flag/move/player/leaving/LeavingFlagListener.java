package org.zone.region.flag.move.player.leaving;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.ZoneManager;

import java.util.Optional;

public class LeavingFlagListener {

    @Listener
    public void onPlayerMove(MoveEntityEvent event, @Getter("entity") Player player) {
        ZoneManager manager = ZonePlugin.getZonesPlugin().getZoneManager();
        @NotNull Optional<Zone> opOriginalZone = manager.getPriorityZone(player.world(),
                                                                         event.originalPosition());
        Optional<Zone> opNextZone = manager.getPriorityZone(player.world(),
                                                            event.destinationPosition());
    }
}
