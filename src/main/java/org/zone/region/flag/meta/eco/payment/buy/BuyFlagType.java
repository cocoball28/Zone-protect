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
import org.zone.region.shop.transaction.price.Price;
import org.zone.region.shop.transaction.price.PriceBuilder;
import org.zone.region.shop.transaction.price.PriceType;
import org.zone.region.shop.transaction.price.player.PlayerEcoPrice;

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
        double amount = node.node("amount").getDouble();
        String typeString = node.node("type").getString();
        if (typeString == null) {
            throw new IOException("\"type\" cannot be null");
        }
        PriceType type;
        try {
            type = PriceType.valueOf(typeString);
        } catch (IllegalArgumentException e) {
            throw new IOException(e);
        }
        if (type == PriceType.ECO) {
            String currencyString = node.node("currency").getString();
            ResourceKey currencyKey = ResourceKey.resolve(currencyString);
            Currency currency = RegistryTypes.CURRENCY
                    .get()
                    .findValue(currencyKey)
                    .orElseThrow(() -> new IOException("Unknown currency of " + currencyString));
            return new BuyFlag(new PlayerEcoPrice(currency, BigDecimal.valueOf(amount)));
        }
        return new BuyFlag(new PriceBuilder().setType(type).setAmount(amount).buildPlayer());
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable BuyFlag save) throws IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        Price.PlayerPrice<?> price = save.getPrice();
        node.node("amount").set(price.getAmount().doubleValue());
        node.node("type").set(price.getType().name());
        if (price instanceof Price.EcoPrice<?> eco) {
            node.node("currency").set(eco.getCurrency().key(RegistryTypes.CURRENCY).asString());
        }
    }

    @Override
    public @NotNull Optional<BuyFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
