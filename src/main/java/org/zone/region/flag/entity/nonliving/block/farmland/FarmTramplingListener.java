package org.zone.region.flag.entity.nonliving.block.farmland;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.zone.ZonePlugin;
import org.zone.region.Zone;

import java.util.Optional;

public class FarmTramplingListener {

    @Listener
    public void onFarmLandTrample(CollideBlockEvent.Fall event) {
        Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.targetLocation());
    }
}
