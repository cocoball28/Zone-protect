package org.zone.region.flag.meta.eco.price.player;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.zone.region.flag.meta.eco.price.Price;

import java.math.BigDecimal;
import java.util.Optional;

public class PlayerEcoPrice implements Price.PlayerPrice {

    private final @NotNull Currency currency;
    private final @NotNull BigDecimal amount;

    public PlayerEcoPrice(@NotNull Currency currency, @NotNull BigDecimal decimal) {
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
    public boolean hasEnough(Player player) {
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
    public Component getDisplayName() {
        return this.currency.symbol().append(Component.text(this.amount.toString()));
    }
}
