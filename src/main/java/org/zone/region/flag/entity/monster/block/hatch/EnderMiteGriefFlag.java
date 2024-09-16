package org.zone.region.flag.entity.monster.block.hatch;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class EnderMiteGriefFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull EnderMiteGriefFlagType getType() {
        return FlagTypes.ENDER_MITE_GRIEF;
    }
}
