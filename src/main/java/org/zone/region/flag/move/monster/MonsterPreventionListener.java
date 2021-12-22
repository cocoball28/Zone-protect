package org.zone.region.flag.move.monster;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.Monster;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;

import java.util.Optional;

/**
 * The listener for the {@link PreventMonsterFlag}
 */
public class MonsterPreventionListener {

    @Listener
    public void onMonsterMoveForMonsterPrevention(MoveEntityEvent event,
                                                  @Getter("entity") Monster monster) {
        if (event.originalPosition().toInt().equals(event.destinationPosition().toInt())) {
            return;
        }
        @NotNull Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.destinationPosition());
        if (opZone.isEmpty()) {
            return;
        }
        Zone zone = opZone.get();
        @NotNull Optional<PreventMonsterFlag> opFlag = zone.getFlag(FlagTypes.PREVENT_MONSTER);
        if (opFlag.isEmpty()) {
            return;
        }
        event.setCancelled(true);
    }
}
