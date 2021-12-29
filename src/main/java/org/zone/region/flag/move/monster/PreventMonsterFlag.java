package org.zone.region.flag.move.monster;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

/**
 * A flag designed to block monsters from walking into the zone
 */
public class PreventMonsterFlag implements Flag.TaggedFlag {

    @Override
    public @NotNull PreventMonsterFlagType getType() {
        return FlagTypes.PREVENT_MONSTER;
    }
}
