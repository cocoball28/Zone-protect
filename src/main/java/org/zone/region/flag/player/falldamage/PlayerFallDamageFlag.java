package org.zone.region.flag.player.falldamage;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

public class PlayerFallDamageFlag implements Flag.TaggedFlag {

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.PLAYER_FALL_DAMAGE_FLAG_TYPE;
    }

}
