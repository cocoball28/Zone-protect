package org.zone.region.flag.meta.join.visibility.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.meta.join.visibility.ZoneVisibility;

import java.io.IOException;
import java.util.Optional;

public class ZoneVisibilityFlagType implements FlagType<ZoneVisibilityFlag> {

    public static final String NAME = "Visibility Flag";
    public static final PluginContainer PLUGIN = ZonePlugin.getZonesPlugin().getPluginContainer();
    public static final String KEY = "visibility";

    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return PLUGIN;
    }

    @Override
    public @NotNull String getKey() {
        return KEY;
    }

    @Override
    public @NotNull ZoneVisibilityFlag load(@NotNull ConfigurationNode node)
            throws IOException {
        String name = node.node("ZoneVisibility").getString();
        if (name == null) {
            throw new IOException("Unknown Visibility");
        }
        try {
            ZoneVisibility zoneVisibilityName = ZoneVisibility.valueOf(name);
            ZoneVisibilityFlag zoneVisibilityFlag = new ZoneVisibilityFlag();
            zoneVisibilityFlag.setZoneVisibility(zoneVisibilityName);
            return zoneVisibilityFlag;
        }catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        throw new IOException("Unknown Visibility");
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable ZoneVisibilityFlag save)
            throws IOException {
        if (save == null) {
            return;
        }

        ZoneVisibility zoneVisibility = save.getZoneVisibility();
        String visibilityName = zoneVisibility.name();
        node.node("ZoneVisibility").set(visibilityName);
    }

    @Override
    public @NotNull Optional<ZoneVisibilityFlag> createCopyOfDefaultFlag() {
        return Optional.of(new ZoneVisibilityFlag());
    }
}
