package org.zone.region.flag.entity.player.interact.block.destroy;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

/**
 * A flag to check if a player can break a block found within a region.
 *
 * If the player is within a group that has the specified GroupKey then they can break blocks even with the flag enabled
 */
public class BlockBreakFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {

    @Override
    public @NotNull BlockBreakFlagType getType() {
        return FlagTypes.BLOCK_BREAK;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.BLOCK_BREAK;
    }


}