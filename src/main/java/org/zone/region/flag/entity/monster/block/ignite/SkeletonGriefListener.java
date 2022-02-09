package org.zone.region.flag.entity.monster.block.ignite;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.block.transaction.BlockTransaction;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.monster.skeleton.Skeleton;
import org.spongepowered.api.entity.projectile.arrow.ArrowEntity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkeletonGriefListener {

    @Listener
    public void onSkeletonSetBlocksOnFire(ChangeBlockEvent.All event, @First ArrowEntity arrow) {
        if (arrow.get(Keys.FIRE_TICKS).isEmpty() && arrow.shooter().isEmpty() && !(arrow.shooter().get().get() instanceof Skeleton)) {
            return;
        }
        Map<BlockTransaction, Zone> inZone = event
                .transactions()
                .stream()
                .filter(t -> t.original().location().isPresent())
                .map(t -> {
                    Location<?, ?> loc = arrow
                            .location()
                            .world()
                            .location(t.original().position());
                    @NotNull Optional<Zone> opZone = ZonePlugin
                            .getZonesPlugin()
                            .getZoneManager()
                            .getPriorityZone(loc);
                    return new AbstractMap.SimpleEntry<>(t, opZone.orElse(null));
                })
                .filter(map -> map.getValue() != null)
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue));

        inZone
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().operation() == Operations.PLACE.get())
                .filter(entry -> entry.getValue().containsFlag(FlagTypes.SKELETON_GRIEF))
                .forEach(entry -> {
                    entry
                            .getValue()
                            .containsFlag(FlagTypes.SKELETON_GRIEF);
                    entry.getKey().invalidate();
                });
    }
}
