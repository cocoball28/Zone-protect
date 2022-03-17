package org.zone.region.flag.entity.nonliving.block.tnt;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.explosive.fused.PrimedTNT;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.math.vector.Vector3i;
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
                    if (opZone.isEmpty()) {
                        return false;
                    }
                    Zone zone = opZone.get();
                    Optional<TnTDefuseFlag> opFlag = zone.getFlag(FlagTypes.TNT_DEFUSE);
                    return opFlag.isPresent();
                });
        if (contains) {
            event.setCancelled(true);
        }
    }

    @Listener
    public void onTntExplodeEvent(ExplosionEvent.Pre event) {
        if (event.explosion().sourceExplosive().isEmpty()) {
            return;
        }
        if (!(event.explosion().sourceExplosive().get() instanceof PrimedTNT)) {
            return;
        }
        Location<?,?> location = event.explosion().location();
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

    @Listener
    public void onTntInteractEvent(InteractBlockEvent.Secondary event, @First Player player) {
        if (!(event.block().state().type().equals(BlockTypes.TNT.get()))) {
            return;
        }

        Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(player.location().world(), event.interactionPoint());
        if (opZone.isEmpty()) {
            return;
        }

        if (opZone.get().containsFlag(FlagTypes.TNT_DEFUSE)) {
            event.setCancelled(true);
        }

    }
}
