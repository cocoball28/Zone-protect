package org.zone.region.flag.meta.eco.shop;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Serializable;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;
import org.zone.region.shop.Shop;
import org.zone.region.shop.type.ShopType;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class ShopsFlagType implements FlagType.SerializableType<ShopsFlag> {

    public static final String NAME = "Shops";
    public static final String KEY = "shops";

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
    public @NotNull ShopsFlag load(@NotNull ConfigurationNode node) throws IOException {
        Collection<ShopType<?>> shopTypes = ZonePlugin
                .getZonesPlugin()
                .getShopManager()
                .getRegistered();

        throw new IOException("Not implemented yet");
    }

    @Override
    public void save(
            @NotNull ConfigurationNode node, @Nullable ShopsFlag save) throws IOException {
        ConfigurationNode shopsNode = node.node("shops");
        if (save == null) {
            shopsNode.set(null);
            return;
        }
        for (Shop shop : save.getShops()) {
            ConfigurationNode sNode = shopsNode.appendListNode();
            this.save(sNode, shop);
        }
    }

    private <S extends Shop> void save(ConfigurationNode sNode, S shop) throws IOException {
        Serializable<S> type = (Serializable<S>) shop.getType();
        type.save(sNode, shop);

    }

    @Override
    public @NotNull Optional<ShopsFlag> createCopyOfDefaultFlag() {
        return Optional.of(new ShopsFlag());
    }
}
