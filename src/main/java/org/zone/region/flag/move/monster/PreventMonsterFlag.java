package org.zone.region.flag.move.monster;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

import java.util.Optional;

public class PreventMonsterFlag implements Flag.Enabled {

    private Boolean enabled;

    public static final PreventMonsterFlag DEFAULT = new PreventMonsterFlag(true);

    public PreventMonsterFlag(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public @NotNull Optional<Boolean> getEnabled() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled().orElse(DEFAULT.getEnabled().orElse(false));
    }

    @Override
    public void setEnabled(@Nullable Boolean flag) {
        this.enabled = flag;
    }

    @Override
    public @NotNull PreventMonsterFlagType getType() {
        return FlagTypes.PREVENT_MONSTER;
    }
}
