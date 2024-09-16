package org.zone.region.shop.type;

import org.zone.region.shop.type.inventory.display.DisplayCaseShopType;

public final class ShopTypes {

    public static final DisplayCaseShopType DISPLAY_CASE = new DisplayCaseShopType();

    private ShopTypes() {
        throw new RuntimeException("Should not be created");
    }
}
