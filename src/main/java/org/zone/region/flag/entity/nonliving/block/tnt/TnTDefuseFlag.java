package org.zone.region.flag.entity.nonliving.block.tnt;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

public class TnTDefuseFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.TNT_DEFUSE;
    }
}
