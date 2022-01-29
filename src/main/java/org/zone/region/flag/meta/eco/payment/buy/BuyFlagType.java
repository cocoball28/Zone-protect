package org.zone.region.flag.meta.eco.payment.buy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.meta.eco.price.player.PlayerEcoPrice;
import org.zone.region.flag.meta.eco.price.player.PlayerExpPrice;
import org.zone.region.flag.meta.eco.price.player.PlayerLevelPrice;
import org.zone.region.flag.meta.eco.price.Price;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class BuyFlagType implements FlagType<BuyFlag> {

    private static final String NAME = "Buy";
    private static final String KEY = "buy";


    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return KEY;
    }

    @Override
    public @NotNull BuyFlag load(@NotNull ConfigurationNode node) throws IOException {
        int exp = node.node("exp").getInt();
        int level = node.node("level").getInt();
        double amount = node.node("amount").getDouble();
        String currencyString = node.node("currency").getString();
        if (exp > 0) {
            return new BuyFlag(new PlayerExpPrice(exp));
        }
        if (level > 0) {
            return new BuyFlag(new PlayerLevelPrice(level));
        }
        if (amount <= 0) {
            throw new IOException("Unknown price type. ensure at least 0.1 in either 'amount', " +
                    "'level' or 'exp'");
        }
        if (currencyString == null) {
            throw new IOException("Unknown currency");
        }
        ResourceKey currencyKey = ResourceKey.resolve(currencyString);
        Currency currency = RegistryTypes.CURRENCY
                .get()
                .findValue(currencyKey)
                .orElseThrow(() -> new IOException("Unknown currency of " + currencyString));
        return new BuyFlag(new PlayerEcoPrice(currency, BigDecimal.valueOf(amount)));
    }

    @Override
    public void save(
            @NotNull ConfigurationNode node, @Nullable BuyFlag save) throws IOException {
        if (save == null) {
            node.node("amount").set(null);
            node.node("currency").set(null);
            node.node("level").set(null);
            node.node("exp").set(null);
            return;
        }
        Price price = save.getPrice();
        if (price instanceof PlayerEcoPrice eco) {
            node.node("amount").set(eco.getAmount().doubleValue());
            node.node("currency").set(eco.getCurrency().key(RegistryTypes.CURRENCY).asString());
        } else if (price instanceof PlayerLevelPrice level) {
            node.node("level").set(level.getLevel());
        } else if (price instanceof PlayerExpPrice exp) {
            node.node("exp").set(exp.getExp());
        }
    }

    @Override
    public @NotNull Optional<BuyFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
