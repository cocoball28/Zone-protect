package org.zone.region.flag.interact.itemframe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

public class InteractItemframesFlagType implements FlagType<InteractItemframesFlag> {

    @Override
    public @NotNull String getName() {
        return "Interact Itemframes";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "interactitemframes";
    }

    @Override
    public @NotNull InteractItemframesFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean isEmpty = node.node("Enabled").isNull();
        if (isEmpty) {
            throw new IOException("Could not load flag");
        }
        boolean enabled = node.node("Enabled").getBoolean();
        return new InteractItemframesFlag(enabled);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable InteractItemframesFlag save) throws IOException {
        if (save == null || save.getEnabled().isEmpty()) {
            node.set(null);
            return;
        }
        Optional<Boolean> opvalue = save.getEnabled();
        if (opvalue.isPresent()) {
            node.node("Enabled").set(opvalue);
        }

    }

    @Override
    public @NotNull Optional<InteractItemframesFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
