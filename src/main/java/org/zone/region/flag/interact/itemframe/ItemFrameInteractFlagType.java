package org.zone.region.flag.interact.itemframe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

public class ItemFrameInteractFlagType implements FlagType<ItemFrameInteractFlag> {

    @Override
    public @NotNull String getName() {
        return "Interaction with Item Frames";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "itemframe_interact";
    }

    @Override
    public @NotNull ItemFrameInteractFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean isEmpty = node.node("Enabled").isNull();
        if (isEmpty) {
            throw new IOException("Could not load flag");
        }
        boolean enabled = node.node("Enabled").getBoolean();
        return new ItemFrameInteractFlag(enabled);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable ItemFrameInteractFlag save) throws
            IOException {
        if (save == null || save.getEnabled().isEmpty()) {
            node.set(null);
            return;
        }
        Optional<Boolean> opValue = save.getEnabled();
        if (opValue.isPresent()) {
            node.node("Enabled").set(opValue);
        }

    }

    @Override
    public @NotNull Optional<ItemFrameInteractFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
