package org.zone.region.flag.entity.monster.block.explode.creeper;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class CreeperGriefFlag implements Flag.TaggedFlag {

    @Override
    public @NotNull CreeperGriefFlagType getType() {
        return FlagTypes.CREEPER_GRIEF;
    }

}
