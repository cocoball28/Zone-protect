package org.zone.region.flag.entity.nonliving.tnt;

import org.spongepowered.api.entity.explosive.fused.PrimedTNT;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;

import java.util.Optional;

public class TnTDefuseListener {
    @Listener
    public void onTntSpawnEvent(SpawnEntityEvent event) {
        boolean contains = event
                .entities()
                .stream()
                .filter(entity -> entity instanceof PrimedTNT)
                .anyMatch(entity -> {
                    entity.position();
                    Optional<Zone> opZone = ZonePlugin
                            .getZonesPlugin()
                            .getZoneManager()
                            .getPriorityZone(entity.location());
                    Zone zone = opZone.get();
                    Optional<TnTDefuseFlag> opFlag = zone.getFlag(FlagTypes.TNT_DEFUSE_FLAG_TYPE);

                });
        if (contains) {
            event.setCancelled(true);
        }
    }
}
