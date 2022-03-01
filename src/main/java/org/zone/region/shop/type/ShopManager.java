package org.zone.region.shop.type;

import org.zone.ZonePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ShopManager {

    private final Collection<ShopType<?>> shopTypes = new HashSet<>(ZonePlugin
            .getZonesPlugin()
            .getVanillaTypes(ShopType.class)
            .map(type -> (ShopType<?>) type)
            .collect(Collectors.toSet()));

    public Collection<ShopType<?>> getRegistered() {
        return this.shopTypes;
    }
}
