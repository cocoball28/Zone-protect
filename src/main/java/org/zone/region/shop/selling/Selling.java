package org.zone.region.shop.selling;

import org.jetbrains.annotations.NotNull;
import org.zone.region.shop.transaction.price.Price;

/**
 * A item a shop is selling
 *
 * @param <I> The item class
 * @param <P> The price type
 * @since 1.0.1
 */
public interface Selling<I, P extends Price.PlayerPrice<?>> {

    /**
     * Gets the price that the item is being sold for
     *
     * @return The price
     * @since 1.0.1
     */
    @NotNull P getPrice();

    /**
     * Gets the item that is being sold
     *
     * @return The item
     * @since 1.0.1
     */
    @NotNull I getItem();

    /**
     * Gets the amount of that single item being sold
     *
     * @return The amount
     * @since 1.0.1
     */
    int getAmount();


}
