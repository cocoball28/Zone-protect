package org.zone.region.flag.player.entitydamage;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

public class EntityDamagePlayerFlag implements Flag.TaggedFlag, Flag.AffectsPlayer {

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.ENTITY_DAMAGE_PLAYER_FLAG_TYPE;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.ENTITY_DAMAGE_PLAYER;
    }
}