package org.zone.region.flag.entity.player.damage.fall;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class PlayerFallDamageFlagType implements FlagType.TaggedFlagType<PlayerFallDamageFlag> {

    @Override
    public @NotNull String getName() {
        return "Player fall damage";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "player_fall_damage";
    }

    @Override
    public PlayerFallDamageFlag createCopyOfDefault() {
        return new PlayerFallDamageFlag();
    }

}
