package org.zone.region.flag.meta.eco.price.zone;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.service.economy.Currency;
import org.zone.region.Zone;
import org.zone.region.flag.meta.eco.balance.BalanceFlag;
import org.zone.region.flag.meta.eco.price.Price;

import java.math.BigDecimal;

public class ZoneEcoPrice implements Price.ZonePrice {

    private final @NotNull Currency currency;
    private final @NotNull BigDecimal amount;

    public ZoneEcoPrice(@NotNull Currency currency, @NotNull BigDecimal decimal) {
        this.amount = decimal;
        this.currency = currency;
    }

    public @NotNull Currency getCurrency() {
        return this.currency;
    }

    public @NotNull BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public boolean hasEnough(Zone zone) {
        BalanceFlag flag = zone.getEconomy();
        return flag.hasBalance(this.currency, this.amount);
    }

    @Override
    public Component getDisplayName() {
        return this.currency.format(this.amount);
    }
}
