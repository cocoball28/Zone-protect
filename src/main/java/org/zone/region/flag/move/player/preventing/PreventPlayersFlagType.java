package org.zone.region.flag.move.player.preventing;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

public class PreventPlayersFlagType implements FlagType<PreventPlayersFlag> {
    @Override
    public @NotNull String getName() {
        return "Prevent Players";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "preventplayers";
    }

    @Override
    public @NotNull PreventPlayersFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean isEmpty = node.node("Enabled").isNull();
        if (isEmpty) {
            throw new IOException("Could not load flag");
        }
        boolean enabled = node.node("Enabled").getBoolean();
        return new PreventPlayersFlag(enabled);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable PreventPlayersFlag save) throws IOException {
        if (save == null || save.getEnabled().isEmpty()) {
            node.set(null);
            return;
        }
        boolean value = save.isEnabled();
        node.node("Enabled").set(value);
    }

    @Override
    public boolean canApply(Zone zone) {
        return true;
    }

    @Override
    public Optional<PreventPlayersFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
