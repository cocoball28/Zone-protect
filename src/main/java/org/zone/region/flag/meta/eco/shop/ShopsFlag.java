package org.zone.region.flag.meta.eco.shop;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class ShopsFlag implements Flag.Serializable {


    @Override
    public @NotNull ShopsFlagType getType() {
        return FlagTypes.SHOPS;
    }
}
