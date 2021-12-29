package org.zone.region.flag.interact.door;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

/**
 * A flag to check if a player can open/close a door found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can open/close blocks even with the flag enabled
 */
public class DoorInteractionFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {

    @Override
    public @NotNull DoorInteractionFlagType getType() {
        return FlagTypes.DOOR_INTERACTION;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.INTERACT_DOOR;
    }
}
