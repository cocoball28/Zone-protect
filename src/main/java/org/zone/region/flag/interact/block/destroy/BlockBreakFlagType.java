package org.zone.region.flag.interact.block.destroy;

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
 * A flag to check if a player can break a block found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can break blocks even with the flag enabled
 */
public class BlockBreakFlagType implements FlagType<BlockBreakFlag> {
    @Override
    public @NotNull String getName() {
        return "Block Break";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "block_break";
    }

    @Override
    public @NotNull BlockBreakFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean isEmpty = node.node("Enabled").isNull();
        if (isEmpty) {
            throw new IOException("Cannot load flag");
        }
        boolean enabled = node.node("Enabled").getBoolean();
        return new BlockBreakFlag(enabled);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable BlockBreakFlag save) throws
            IOException {
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
    public Optional<BlockBreakFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
