package org.zone.region.shop.selling;

import org.jetbrains.annotations.NotNull;
import org.zone.region.shop.transaction.price.Price;

public interface Selling<I, P extends Price.PlayerPrice<?>> {

    @NotNull P getPrice();

    @NotNull I getItem();

    int getAmount();


}
