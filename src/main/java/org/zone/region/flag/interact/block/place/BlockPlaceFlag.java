package org.zone.region.flag.interact.block.place;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

/**
 * A flag to check if a player can place a block found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can place blocks even with the flag enabled
 */
public class BlockPlaceFlag implements Flag.Enabled, Flag.GroupKeyed {

    private Boolean enabled;

    public static final BlockPlaceFlag DEFAULT = new BlockPlaceFlag(false);

    public BlockPlaceFlag(@SuppressWarnings("TypeMayBeWeakened") @NotNull BlockPlaceFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public BlockPlaceFlag(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.BLOCK_PLACE;
    }

    @Override
    public void setEnabled(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Optional<Boolean> getEnabled() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled().orElse(DEFAULT.getEnabled().orElse(false));
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.BLOCK_PLACE;
    }
}
