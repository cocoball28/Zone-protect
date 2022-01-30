package org.zone.region.flag.meta.eco.payment.buy;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.eco.price.Price;

public class BuyFlag implements Flag {

    private final @NotNull Price.PlayerPrice<? extends Number> price;

    public BuyFlag(@NotNull Price.PlayerPrice<? extends Number> price) {
        this.price = price;
    }

    public @NotNull Price.PlayerPrice<? extends Number> getPrice() {
        return this.price;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.BUY;
    }
}
