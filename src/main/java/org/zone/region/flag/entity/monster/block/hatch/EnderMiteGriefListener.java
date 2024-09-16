package org.zone.region.flag.entity.monster.block.hatch;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.block.transaction.BlockTransaction;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.entity.living.monster.Endermite;
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

public class EnderMiteGriefListener {

    @Listener
    public void onEnderMiteBreakBlockOnHatch(
            ChangeBlockEvent.All event,
            @First Endermite endermite) {
        Map<BlockTransaction, Zone> inZone = event
                .transactions()
                .stream()
                .filter(t -> t.original().location().isPresent())
                .map(t -> {
                    Location<?, ?> loc = endermite
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
                .filter(entry -> entry.getValue().getFlag(FlagTypes.ENDER_MITE_GRIEF).isPresent())
                .forEach(entry -> {
                    entry.getValue().containsFlag(FlagTypes.ENDER_MITE_GRIEF);
                    entry.getKey().invalidate();
                });
    }
}
