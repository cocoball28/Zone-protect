package org.zone.region.flag.entity.monster.block.take;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.block.transaction.BlockTransaction;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.entity.living.monster.Enderman;
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

public class EnderManGriefListener {

    @Listener
    public void onEnderManTakeBlocks(ChangeBlockEvent.All event, @First Enderman enderman) {
        Map<BlockTransaction, Zone> inZone = event
                .transactions()
                .stream()
                .filter(t -> t.original().location().isPresent())
                .map(t -> {
                    Location<?, ?> loc = enderman
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
                .filter(entry -> entry.getKey().operation() == Operations.BREAK.get() ||
                        entry.getKey().operation() == Operations.PLACE.get())
                .filter(entry -> entry.getValue().getFlag(FlagTypes.ENDER_MAN_GRIEF).isPresent())
                .forEach(entry -> {
                    entry.getValue().containsFlag(FlagTypes.ENDER_MAN_GRIEF);
                    entry.getKey().invalidate();
                });
    }
}
