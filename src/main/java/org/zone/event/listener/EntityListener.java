package org.zone.event.listener;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.Monster;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.move.monster.PreventMonsterFlag;

import java.util.Optional;

public class EntityListener {

    @Listener
    public void onMonsterMoveForMonsterPrevention(MoveEntityEvent event, @Getter("entity") Monster monster) {
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
