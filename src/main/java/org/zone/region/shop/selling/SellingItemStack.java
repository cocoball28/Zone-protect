package org.zone.region.shop.selling;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.zone.region.shop.transaction.price.player.PlayerEcoPrice;

public class SellingItemStack implements Selling<ItemStackSnapshot, PlayerEcoPrice> {

    private final int amount;
    private final @NotNull ItemStackSnapshot item;
    private final @NotNull PlayerEcoPrice price;

    public SellingItemStack(@NotNull PlayerEcoPrice price, @NotNull ItemStackSnapshot item) {
        this(price, item, 1);
    }

    public SellingItemStack(
            @NotNull PlayerEcoPrice price, @NotNull ItemStackSnapshot item, int amount) {
        this.amount = amount;
        this.item = item;
        this.price = price;
    }

    @Override
    public @NotNull PlayerEcoPrice getPrice() {
        return this.price;
    }

    @Override
    public @NotNull ItemStackSnapshot getItem() {
        return this.item;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }
}
