package org.zone.region.flag.move.player.preventing;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class PreventPlayersFlag implements Flag.Enabled, Flag.GroupKeyed {

    public @Nullable Boolean enabled;
    public static final PreventPlayersFlag ELSE = new PreventPlayersFlag(false);

    public PreventPlayersFlag(@NotNull PreventPlayersFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public PreventPlayersFlag(){
        this((Boolean)null);
    }

    public PreventPlayersFlag(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setEnabled(@Nullable Boolean flag) {
        this.enabled = flag;
    }

    @Override
    public Optional<Boolean> getEnabled() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled().orElse(ELSE.getEnabled().orElse(false));
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.PLAYER_PREVENTION;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.PREVENT_PLAYERS;
    }

}
