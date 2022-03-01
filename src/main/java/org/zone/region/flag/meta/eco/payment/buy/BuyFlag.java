package org.zone.region.flag.meta.eco.payment.buy;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.shop.transaction.price.Price;

public class BuyFlag implements Flag.Serializable {

    private final @NotNull Price.PlayerPrice<? extends Number> price;

    public BuyFlag(@NotNull Price.PlayerPrice<? extends Number> price) {
        this.price = price;
    }

    public @NotNull Price.PlayerPrice<? extends Number> getPrice() {
        return this.price;
    }

    @Override
    public @NotNull BuyFlagType getType() {
        return FlagTypes.BUY;
    }
}
