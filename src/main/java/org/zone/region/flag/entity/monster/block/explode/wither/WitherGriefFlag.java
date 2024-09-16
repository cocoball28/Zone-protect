package org.zone.region.flag.entity.monster.block.explode.wither;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class WitherGriefFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull WitherGriefFlagType getType() {
        return FlagTypes.WITHER_GRIEF;
    }
}
