package org.zone.region.flag.interact.itemframe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class ItemFrameInteractFlag implements Flag.AffectsPlayer, Flag.Enabled {

    public @Nullable Boolean enabled;
    public static final ItemFrameInteractFlag ELSE = new ItemFrameInteractFlag(false);

    public ItemFrameInteractFlag(@SuppressWarnings("TypeMayBeWeakened") @NotNull ItemFrameInteractFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public ItemFrameInteractFlag() {
        this((Boolean)null);
    }

    public ItemFrameInteractFlag(@Nullable Boolean enabled) {
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
        return FlagTypes.ITEM_FRAME_INTERACT;
    }

}
