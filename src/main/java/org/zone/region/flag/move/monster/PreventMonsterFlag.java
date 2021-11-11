package org.zone.region.flag.move.monster;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

import java.util.Optional;

public class PreventMonsterFlag implements Flag.Single<Boolean> {

    private Boolean enabled;

    public static final PreventMonsterFlag DEFAULT = new PreventMonsterFlag(true);

    public PreventMonsterFlag(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public @NotNull Optional<Boolean> getValue() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public void setValue(@Nullable Boolean flag) {
        this.enabled = flag;
    }

    @Override
    public @NotNull PreventMonsterFlagType getType() {
        return FlagTypes.PREVENT_MONSTER;
    }
}
