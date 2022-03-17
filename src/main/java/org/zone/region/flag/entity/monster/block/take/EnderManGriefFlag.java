package org.zone.region.flag.entity.monster.block.take;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

public class EnderManGriefFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull EnderManGriefFlagType getType() {
        return FlagTypes.ENDER_MAN_GRIEF;
    }
}
