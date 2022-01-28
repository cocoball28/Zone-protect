package org.zone.region.flag.entity.player.damage.fire;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

public class PlayerFireDamageFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {
    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.PLAYER_FALL_DAMAGE;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.PLAYER_FIRE_DAMAGE_FLAG_TYPE;
    }
}
