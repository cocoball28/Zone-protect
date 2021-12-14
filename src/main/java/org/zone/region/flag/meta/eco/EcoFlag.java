package org.zone.region.flag.meta.eco;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.eco.transaction.DepositTransaction;
import org.zone.region.flag.meta.eco.transaction.TransactionBuilder;
import org.zone.region.flag.meta.eco.transaction.TransactionState;
import org.zone.region.flag.meta.eco.transaction.WithdrawTransaction;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EcoFlag implements Flag {

    private final @NotNull Map<Currency, BigDecimal> money = new HashMap<>();

    public EcoFlag() {
        this(new HashMap<>());
    }

    public EcoFlag(Map<? extends Currency, ? extends Number> money) {
        this.money.putAll(money
                .entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), BigDecimal.valueOf(entry
                        .getValue()
                        .doubleValue())))
                .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue)));
    }

    public @NotNull Map<Currency, BigDecimal> getMoney() {
        return Collections.unmodifiableMap(this.money);
    }

    public @NotNull BigDecimal getMoney(@NotNull Currency currency) {
        return this.money.getOrDefault(currency, BigDecimal.valueOf(Sponge
                .serviceProvider()
                .provide(EconomyService.class)
                .isPresent() ? 0 : Double.MAX_VALUE));
    }

    public boolean hasBalance(@NotNull Currency currency, @NotNull BigDecimal amount) {
        return this.getMoney(currency).compareTo(amount) >= 0;
    }

    public void setBalance(@NotNull Currency currency, @NotNull BigDecimal amount) {
        if (Sponge.serviceProvider().provide(EconomyService.class).isEmpty()) {
            return;
        }
        if (this.money.containsKey(currency)) {
            this.money.replace(currency, amount);
            return;
        }
        this.money.put(currency, amount);
    }

    public @NotNull DepositTransaction deposit(@NotNull Currency currency, @NotNull BigDecimal amount) {
        if (Sponge.serviceProvider().provide(EconomyService.class).isEmpty()) {
            return new DepositTransaction(new TransactionBuilder()
                    .setFlag(this)
                    .setAfter(BigDecimal.valueOf(Double.MAX_VALUE))
                    .setOriginal(BigDecimal.valueOf(Double.MAX_VALUE))
                    .setState(TransactionState.FAIL));
        }
        BigDecimal money = this.getMoney(currency);
        BigDecimal nextMoney = money.add(amount);
        this.setBalance(currency, money);
        return new DepositTransaction(new TransactionBuilder()
                .setState(TransactionState.SUCCESS)
                .setOriginal(money)
                .setAfter(nextMoney)
                .setFlag(this));
    }

    public @NotNull WithdrawTransaction withdraw(@NotNull Currency currency, @NotNull BigDecimal amount) {
        if (Sponge.serviceProvider().provide(EconomyService.class).isEmpty()) {
            return new WithdrawTransaction(new TransactionBuilder()
                    .setFlag(this)
                    .setAfter(BigDecimal.valueOf(Double.MAX_VALUE))
                    .setOriginal(BigDecimal.valueOf(Double.MAX_VALUE))
                    .setState(TransactionState.FAIL));
        }
        BigDecimal money = this.getMoney(currency);
        BigDecimal nextMoney = money.subtract(amount);
        this.setBalance(currency, money);
        return new WithdrawTransaction(new TransactionBuilder()
                .setState(TransactionState.SUCCESS)
                .setOriginal(money)
                .setAfter(nextMoney)
                .setFlag(this));
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.ECO;
    }
}
