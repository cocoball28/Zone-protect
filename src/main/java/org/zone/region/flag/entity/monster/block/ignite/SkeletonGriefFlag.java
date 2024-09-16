package org.zone.region.flag.entity.monster.block.ignite;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class SkeletonGriefFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull SkeletonGriefFlagType getType() {
        return FlagTypes.SKELETON_GRIEF;
    }
}
