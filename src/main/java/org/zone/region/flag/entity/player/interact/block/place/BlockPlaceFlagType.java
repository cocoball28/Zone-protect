package org.zone.region.flag.entity.player.interact.block.place;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

/**
 * A flag to check if a player can place a block found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can place blocks even with the flag enabled
 */
public class BlockPlaceFlagType implements FlagType.TaggedFlagType<BlockPlaceFlag> {

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
    public BlockPlaceFlag createCopyOfDefault() {
        return new BlockPlaceFlag();
    }
}
