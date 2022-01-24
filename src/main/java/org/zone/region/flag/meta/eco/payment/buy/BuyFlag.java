package org.zone.region.flag.meta.eco.payment.buy;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.eco.price.Price;

public class BuyFlag implements Flag {

    private final @NotNull Price price;

    public BuyFlag(@NotNull Price price) {
        this.price = price;
    }

    public @NotNull Price getPrice() {
        return this.price;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.BUY;
    }
}
