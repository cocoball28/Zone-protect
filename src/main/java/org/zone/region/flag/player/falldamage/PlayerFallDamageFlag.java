package org.zone.region.flag.player.falldamage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.move.player.preventing.PreventPlayersFlag;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class PlayerFallDamageFlag implements Flag.TaggedFlag, Flag.Enabled, Flag.AffectsPlayer{

    public @Nullable Boolean enabled;
    public static final PlayerFallDamageFlag ELSE = new PlayerFallDamageFlag(false);

    public PlayerFallDamageFlag(@NotNull PreventPlayersFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public PlayerFallDamageFlag() {
        this((Boolean) null);
    }
    public PlayerFallDamageFlag(@Nullable Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public @NotNull Optional<Boolean> getEnabled() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled().orElse(ELSE.getEnabled().orElse(false));
    }

    @Override
    public void setEnabled(@Nullable Boolean flag) {
        this.enabled = flag;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.PLAYER_FALL_DAMAGE;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.PLAYER_FALL_DAMAGE_FLAG_TYPE;
    }
}
