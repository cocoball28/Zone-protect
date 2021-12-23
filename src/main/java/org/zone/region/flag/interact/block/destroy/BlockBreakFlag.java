package org.zone.region.flag.interact.block.destroy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

/**
 * A flag to check if a player can break a block found within a region.
 *
 * If the player is within a group that has the specified GroupKey then they can break blocks even with the flag enabled
 */
public class BlockBreakFlag implements Flag.Enabled, Flag.GroupKeyed {

    private @Nullable Boolean enabled;

    /**
     * The default flag values
     */
    public static final BlockBreakFlag ELSE = new BlockBreakFlag(false);

    @SuppressWarnings("CopyConstructorMissesField")
    public BlockBreakFlag(@SuppressWarnings("TypeMayBeWeakened") @NotNull BlockBreakFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public BlockBreakFlag(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public @NotNull BlockBreakFlagType getType() {
        return FlagTypes.BLOCK_BREAK;
    }    @Override
    public @NotNull Optional<Boolean> getEnabled() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.BLOCK_BREAK;
    }    @Override
    public boolean isEnabled() {
        return this.getEnabled().orElse(ELSE.getEnabled().orElse(false));
    }

    @Override
    public void setEnabled(@Nullable Boolean flag) {
        this.enabled = flag;
    }




}