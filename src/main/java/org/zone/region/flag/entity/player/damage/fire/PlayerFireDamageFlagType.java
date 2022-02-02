package org.zone.region.flag.entity.player.damage.fire;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class PlayerFireDamageFlagType implements FlagType.TaggedFlagType<PlayerFireDamageFlag> {

    public static final String NAME = "Prevent Fire Damage To Players";
    public static final String KEY = "fire_damage_prevention";

    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return KEY;
    }

    @Override
    public PlayerFireDamageFlag createCopyOfDefault() {
        return new PlayerFireDamageFlag();
    }
}
