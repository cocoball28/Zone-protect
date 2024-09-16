package org.zone.region.flag.entity.player.damage.attack;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

public class EntityDamagePlayerFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {

    @Override
    public @NotNull EntityDamagePlayerFlagType getType() {
        return FlagTypes.ENTITY_DAMAGE_PLAYER;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.ENTITY_DAMAGE_PLAYER;
    }
}