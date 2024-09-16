package org.zone.region.flag.entity.player.interact.itemframe;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

public class ItemFrameInteractFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.INTERACT_ITEMFRAME;
    }

    @Override
    public @NotNull ItemFrameInteractFlagType getType() {
        return FlagTypes.ITEM_FRAME_INTERACT;
    }

}