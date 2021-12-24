package org.zone.region.flag.interact.door;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

/**
 * A flag to check if a player can open/close a door found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can open/close blocks even with the flag enabled
 */
public class DoorInteractionFlag implements Flag.Enabled, Flag.AffectsPlayer {

    private @Nullable Boolean enabled;

    public static final DoorInteractionFlag ELSE = new DoorInteractionFlag(false);

    @SuppressWarnings("CopyConstructorMissesField")
    public DoorInteractionFlag(@SuppressWarnings("TypeMayBeWeakened") @NotNull DoorInteractionFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public DoorInteractionFlag(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public @NotNull DoorInteractionFlagType getType() {
        return FlagTypes.DOOR_INTERACTION;
    }

    @Override
    public @NotNull Optional<Boolean> getEnabled() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.INTERACT_DOOR;
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled().orElse(ELSE.getEnabled().orElse(false));
    }

    @Override
    public void setEnabled(@Nullable Boolean flag) {
        this.enabled = flag;
    }


}
