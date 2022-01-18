package org.zone.region.flag.entity.nonliving.tnt;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;

public class TnTDefuseFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull FlagType<?> getType() {
        return null;
    }
}
