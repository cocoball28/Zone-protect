package org.zone.region.flag.meta.eco.shop;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.shop.Shop;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class ShopsFlag implements Flag.Serializable {

    private final Collection<Shop> shops = new HashSet<>();

    public ShopsFlag() {
        this(Collections.emptyList());
    }

    public ShopsFlag(Collection<? extends Shop> shops) {
        this.shops.addAll(shops);
    }

    public Collection<Shop> getShops() {
        return Collections.unmodifiableCollection(this.shops);
    }


    @Override
    public @NotNull ShopsFlagType getType() {
        return FlagTypes.SHOPS;
    }
}
