package org.zone.region.flag.entity.monster.block.knock;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.block.transaction.BlockTransaction;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.entity.living.monster.zombie.Zombie;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.tag.BlockTypeTags;
import org.spongepowered.api.world.Location;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ZombieGriefListener {

    @Listener
    public void onZombieTryBreakDoor(ChangeBlockEvent.All event, @First Zombie zombie) {
        Map<BlockTransaction, Zone> inZone = event
                .transactions()
                .stream()
                .filter(t -> t.original().location().isPresent())
                .filter(t -> BlockTypeTags.DOORS.get().contains(t.original().state().type()))
                .map(t -> {
                    Location<?, ?> loc = zombie
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
                .filter(entry -> entry.getKey().operation() == Operations.BREAK.get())
                .filter(entry -> entry.getValue().containsFlag(FlagTypes.ZOMBIE_GRIEF))
                .forEach(entry -> {
                    entry.getValue().containsFlag(FlagTypes.ZOMBIE_GRIEF);
                    entry.getKey().invalidate();
                });
    }
}
