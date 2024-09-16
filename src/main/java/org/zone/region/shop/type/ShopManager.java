package org.zone.region.shop.type;

import org.jetbrains.annotations.NotNull;
import org.zone.IdentifiableManager;
import org.zone.ZonePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ShopManager implements IdentifiableManager.Typed<ShopType<?>> {

    private final Collection<ShopType<?>> shopTypes = new HashSet<>(ZonePlugin
            .getZonesPlugin()
            .getVanillaTypes(ShopType.class)
            .map(type -> (ShopType<?>) type)
            .collect(Collectors.toSet()));

    @Override
    public Collection<ShopType<?>> getRegistered() {
        return this.shopTypes;
    }

    @Override
    public void register(@NotNull ShopType<?> type) {
        this.shopTypes.add(type);
    }
}
