package org.zone.region.flag.meta.eco.shop;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
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
        return null;
    }

    @Override
    public void save(
            @NotNull ConfigurationNode node, @Nullable ShopsFlag save) throws IOException {

    }

    @Override
    public @NotNull Optional<ShopsFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
