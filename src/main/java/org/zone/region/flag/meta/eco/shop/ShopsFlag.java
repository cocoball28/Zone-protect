package org.zone.region.flag.meta.eco.shop;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.shop.Shop;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

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

    public Optional<Shop> getShop(Vector3i vector) {
        return this
                .getShops()
                .stream()
                .filter(shop -> shop.getLocation().blockPosition().equals(vector))
                .findAny();
    }

    public void register(Shop shop) {
        this.shops.add(shop);
    }


    @Override
    public @NotNull ShopsFlagType getType() {
        return FlagTypes.SHOPS;
    }
}
