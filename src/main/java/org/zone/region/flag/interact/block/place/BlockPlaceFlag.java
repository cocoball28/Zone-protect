package org.zone.region.flag.interact.block.place;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

/**
 * A flag to check if a player can place a block found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can place blocks even with the flag enabled
 */
public class BlockPlaceFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.BLOCK_PLACE;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.BLOCK_PLACE;
    }

}
