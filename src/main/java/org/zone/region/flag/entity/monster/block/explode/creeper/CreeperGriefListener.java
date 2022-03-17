package org.zone.region.flag.entity.monster.block.explode.creeper;

import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.math.vector.Vector3i;
import org.zone.ZonePlugin;

import java.util.Optional;

public class CreeperGriefListener {

    @Listener
    public void onCreeperExplode(ExplosionEvent.Pre event) {
        if (event.explosion().sourceExplosive().isEmpty()) {
            return;
        }
        if (!(event.explosion().sourceExplosive().get() instanceof Creeper)) {
            return;
        }
        Location<?, ?> location = event.explosion().location();
        float explosionRadius = event.explosion().radius();
        boolean contains = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getRegistered()
                .stream()
                .anyMatch(zone -> {
                    Optional<Vector3i> nearestPos = zone
                            .getRegion()
                            .getNearestPosition(location.blockPosition());
                    if (nearestPos.isEmpty()) {
                        return false;
                    }
                    double distance = nearestPos
                            .get()
                            .toDouble()
                            .distance(location.position());
                    return distance <= explosionRadius;
                });
                if (contains) {
                    event.setCancelled(true);
                }
    }
}
