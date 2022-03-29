package org.zone.region.flag.entity.player.damage.fall;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

public class PlayerFallDamageFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {

    @Override
    public @NotNull PlayerFallDamageFlagType getType() {
        return FlagTypes.PLAYER_FALL_DAMAGE;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.PLAYER_FALL_DAMAGE;
    }
}
