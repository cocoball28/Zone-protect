package org.zone.region.shop.selling;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.zone.region.shop.transaction.price.player.PlayerEcoPrice;

/**
 * The class used for selling ItemStacks
 */
public class SellingItemStack implements Selling<ItemStackSnapshot, PlayerEcoPrice> {

    private final int amount;
    private final @NotNull ItemStackSnapshot item;
    private final @NotNull PlayerEcoPrice price;

    /**
     * Creates a ItemStack selling
     *
     * @param price The price to sell it for
     * @param item  The item to sell
     */
    public SellingItemStack(@NotNull PlayerEcoPrice price, @NotNull ItemStackSnapshot item) {
        this(price, item, 1);
    }

    /**
     * Creates a ItemStack selling
     *
     * @param price  The price to sell it for
     * @param item   The item to sell
     * @param amount The amount of stacks -> if you have 4 stacks with a quantiy of 2 items in
     *               that stack, it will give you 8 items
     */
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
