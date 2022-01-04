package org.zone.region.flag.entity.player.move.preventing;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

public class PreventPlayersFlag implements Flag.TaggedFlag, Flag.GroupKeyed {

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.PLAYER_PREVENTION;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.PREVENT_PLAYERS;
    }

}
