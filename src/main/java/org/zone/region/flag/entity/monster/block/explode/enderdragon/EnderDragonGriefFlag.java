package org.zone.region.flag.entity.monster.block.explode.enderdragon;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

public class EnderDragonGriefFlag implements Flag.TaggedFlag {
    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.ENDER_DRAGON_GRIEF;
    }
}
