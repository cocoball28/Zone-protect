package org.zone.region.flag.interact.itemframe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class InteractItemframesFlag implements Flag.AffectsPlayer, Flag.Enabled {

    public @Nullable Boolean enabled;
    public static final InteractItemframesFlag ELSE = new InteractItemframesFlag(false);

    public InteractItemframesFlag(@NotNull InteractItemframesFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public InteractItemframesFlag() {
        this((Boolean)null);
    }

    public InteractItemframesFlag(@Nullable Boolean enabled) {
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
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.INTERACT_ITEMFRAME;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.INTERACT_ITEMFRAMES_FLAG_TYPE;
    }

}
