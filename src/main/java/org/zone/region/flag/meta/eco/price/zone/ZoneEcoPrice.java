package org.zone.region.flag.meta.eco.price.zone;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.service.economy.Currency;
import org.zone.region.Zone;
import org.zone.region.flag.meta.eco.balance.BalanceFlag;
import org.zone.region.flag.meta.eco.price.Price;
import org.zone.region.flag.meta.eco.price.PriceBuilder;
import org.zone.region.flag.meta.eco.price.PriceType;
import org.zone.region.flag.meta.eco.transaction.TransactionState;

import java.math.BigDecimal;

public class ZoneEcoPrice implements Price.ZonePrice<BigDecimal>, Price.EcoPrice<Zone> {

    private final @NotNull Currency currency;
    private final @NotNull BigDecimal amount;

    public ZoneEcoPrice(@NotNull Currency currency, @NotNull BigDecimal decimal) {
        this.amount = decimal;
        this.currency = currency;
    }

    @Override
    public @NotNull Currency getCurrency() {
        return this.currency;
    }

    @Override
    public @NotNull BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public PriceType getType() {
        return PriceType.ECO;
    }

    @Override
    public boolean hasEnough(@NotNull Zone zone) {
        BalanceFlag flag = zone.getEconomy();
        return flag.hasBalance(this.currency, this.amount);
    }

    @Override
    public boolean withdraw(Zone zone) {
        BalanceFlag flag = zone.getEconomy();
        if (!flag.hasBalance(this.currency, this.amount)) {
            return false;
        }
        return flag.withdraw(this.currency, this.amount).getState() == TransactionState.SUCCESS;
    }

    @Override
    public float getPercentLeft(@NotNull Zone zone) {
        if (BigDecimal.ZERO.compareTo(this.amount) == 0.0) {
            return 0;
        }
        BalanceFlag flag = zone.getEconomy();
        BigDecimal amount = flag.getMoney(this.currency);
        BigDecimal difference = amount.min(this.amount);
        if (amount.doubleValue() == 0.0) {
            return 0;
        }
        return (float) (difference.doubleValue() * 100 / amount.doubleValue());
    }

    @Override
    public PriceBuilder asBuilder() {
        return new PriceBuilder()
                .setAmount(this.amount.doubleValue())
                .setCurrency(this.currency)
                .setType(PriceType.ECO);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.currency.format(this.amount);
    }
}
