package org.zone.region.shop.type;

import org.zone.Identifiable;
import org.zone.Serializable;
import org.zone.annotations.Typed;
import org.zone.region.shop.Shop;

@Typed(typesClass = ShopTypes.class)
public interface ShopType<S extends Shop> extends Serializable<S>, Identifiable {
}
