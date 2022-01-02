package org.zone.region.flag.player.entitydamage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.Optional;

public class EntityDamagePlayerFlag implements Flag.TaggedFlag, Flag.AffectsPlayer, Flag.Enabled {

    public @Nullable Boolean enabled;
    public static final EntityDamagePlayerFlag ELSE = new EntityDamagePlayerFlag(false);

    public EntityDamagePlayerFlag(@NotNull EntityDamagePlayerFlag flag) {
        this(flag.getEnabled().orElse(null));
    }

    public EntityDamagePlayerFlag() {
        this((Boolean) null);
    }

    public EntityDamagePlayerFlag(@Nullable Boolean enabled) {
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
        return FlagTypes.ENTITY_DAMAGE_PLAYER_FLAG_TYPE;
    }

    @Override
    public @NotNull GroupKey getRequiredKey() {
        return GroupKeys.ENTITY_DAMAGE_PLAYER;
    }
}