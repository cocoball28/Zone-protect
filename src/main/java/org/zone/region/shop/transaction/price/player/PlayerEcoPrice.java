package org.zone.region.shop.transaction.price.player;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.zone.region.shop.transaction.price.Price;
import org.zone.region.shop.transaction.price.PriceBuilder;
import org.zone.region.shop.transaction.price.PriceType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * A price class for player economy
 *
 * @since 1.0.1
 */
public class PlayerEcoPrice implements Price.PlayerPrice<BigDecimal>, Price.EcoPrice<Player> {

    private final @NotNull Currency currency;
    private final @NotNull BigDecimal amount;

    public PlayerEcoPrice(@NotNull Currency currency, @NotNull BigDecimal decimal) {
        this.amount = decimal;
        this.currency = currency;
    }

    @Override
    public @NotNull Currency getCurrency() {
        return this.currency;
    }

    @Override
    public boolean hasEnough(@NotNull Player player) {
        Optional<EconomyService> opService = Sponge.serviceProvider().provide(EconomyService.class);
        if (opService.isEmpty()) {
            return false;
        }
        if (!opService.get().hasAccount(player.uniqueId())) {
            return false;
        }
        Optional<UniqueAccount> opAccount = opService.get().findOrCreateAccount(player.uniqueId());
        if (opAccount.isEmpty()) {
            return false;
        }
        return opAccount.get().balance(this.currency).compareTo(this.amount) > 0;
    }

    @Override
    public boolean withdraw(Player player) {
        Optional<EconomyService> opService = Sponge.serviceProvider().provide(EconomyService.class);
        if (opService.isEmpty()) {
            return false;
        }
        if (!opService.get().hasAccount(player.uniqueId())) {
            return false;
        }
        Optional<UniqueAccount> opAccount = opService.get().findOrCreateAccount(player.uniqueId());
        if (opAccount.isEmpty()) {
            return false;
        }
        UniqueAccount account = opAccount.get();
        if (account.balance(this.currency).compareTo(this.amount) < 0) {
            return false;
        }
        return account.withdraw(this.currency, this.amount).result() == ResultType.SUCCESS;
    }

    @Override
    public float getPercentLeft(@NotNull Player player) {
        if (BigDecimal.ZERO.compareTo(this.amount) == 0.0) {
            return 0;
        }
        Optional<EconomyService> opService = Sponge.serviceProvider().provide(EconomyService.class);
        if (opService.isEmpty()) {
            return 0;
        }
        if (!opService.get().hasAccount(player.uniqueId())) {
            return 0;
        }
        Optional<UniqueAccount> opAccount = opService.get().findOrCreateAccount(player.uniqueId());
        if (opAccount.isEmpty()) {
            return 0;
        }
        BigDecimal decimal = opAccount.get().balance(this.currency);
        if (BigDecimal.ZERO.compareTo(decimal) == 0) {
            return 0;
        }
        BigDecimal difference = decimal.min(this.amount);
        return (float) (difference.doubleValue() * 100 / decimal.doubleValue());
    }

    @Override
    public @NotNull BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public @NotNull PriceType getType() {
        return PriceType.ECO;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.currency.symbol().append(Component.text(this.amount.toString()));
    }

    @Override
    public @NotNull PriceBuilder asBuilder() {
        return new PriceBuilder()
                .setType(PriceType.ECO)
                .setAmount(this.amount.doubleValue())
                .setCurrency(this.currency);
    }
}
