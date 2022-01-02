package org.zone.region.flag.playerdamage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class PlayerDamageFlag implements Flag.TaggedFlag, Flag.AffectsPlayer, Flag.Enabled {

    public @Nullable Boolean enabled;
    public static final PlayerDamageFlag ELSE = new PlayerDamageFlag(false);

    public PlayerDamageFlag(@NotNull PlayerDamageFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public PlayerDamageFlag() {
        this((Boolean) null);
    }

    public PlayerDamageFlag(@Nullable Boolean enabled) {
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
    public @NotNull FlagType<?> getType() {
        return FlagTypes.DAMAGE_FLAG_TYPE;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.DAMAGE;
    }
}