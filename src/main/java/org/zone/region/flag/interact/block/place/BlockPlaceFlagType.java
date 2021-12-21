package org.zone.region.flag.interact.block.place;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

/**
 * A flag to check if a player can place a block found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can place blocks even with the flag enabled
 */
public class BlockPlaceFlagType implements FlagType<BlockPlaceFlag> {

    public static final String NAME = "Placement";
    public static final String KEY = "placement";

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
    public @NotNull BlockPlaceFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean isEmpty = node.node("Enabled").isNull();
        if (isEmpty) {
            throw new IOException("Cannot load flag");
        }
        boolean enabled = node.node("Enabled").getBoolean();
        return new BlockPlaceFlag(enabled);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable BlockPlaceFlag save) throws IOException {
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
    public Optional<BlockPlaceFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
