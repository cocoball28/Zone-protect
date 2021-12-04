package org.zone.region.flag.interact.block.destroy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class BlockBreakFlag implements Flag.Enabled, Flag.GroupKeyed {

    private @Nullable Boolean enabled;

    public static final BlockBreakFlag ELSE = new BlockBreakFlag(false);

    public BlockBreakFlag(@SuppressWarnings("TypeMayBeWeakened") @NotNull BlockBreakFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public BlockBreakFlag(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public @NotNull Optional<Boolean> getEnabled() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled().orElse(ELSE.getEnabled().orElse(false));
    }

    @Override
    public void setEnabled(@Nullable Boolean flag) {
        this.enabled = flag;
    }

    @Override
    public @NotNull BlockBreakFlagType getType() {
        return FlagTypes.BLOCK_BREAK;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.BLOCK_BREAK;
    }
}