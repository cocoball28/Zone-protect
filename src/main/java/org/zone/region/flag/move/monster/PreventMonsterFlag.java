package org.zone.region.flag.move.monster;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class PreventMonsterFlag implements Flag {

    public static final PreventMonsterFlag DEFAULT = new PreventMonsterFlag();

    @Override
    public @NotNull PreventMonsterFlagType getType() {
        return FlagTypes.PREVENT_MONSTER;
    }
}
