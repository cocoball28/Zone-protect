package org.zone.region.flag.entity.monster.block.knock;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class ZombieGriefFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull ZombieGriefFlagType getType() {
        return FlagTypes.ZOMBIE_GRIEF;
    }
}
