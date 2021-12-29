package org.zone.region.flag.interact.block.destroy;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

/**
 * A flag to check if a player can break a block found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can break blocks even with the flag enabled
 */
public class BlockBreakFlagType implements FlagType.TaggedFlagType<BlockBreakFlag> {

    public static final String NAME = "Block Break";
    public static final String KEY = "block_break";

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
    public BlockBreakFlag createCopyOfDefault() {
        return new BlockBreakFlag();
    }
}
