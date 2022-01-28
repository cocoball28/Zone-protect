package org.zone.region.flag.entity.player.damage.fire;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class PlayerFireDamageFlagType implements FlagType.TaggedFlagType<PlayerFireDamageFlag> {

    String NAME = "Prevent Fire Damage To Players";
    PluginContainer CONTAINER = ZonePlugin.getZonesPlugin().getPluginContainer();
    String KEY = "fire_damage_prevention";

    @Override
    public @NotNull String getName() {
        return this.NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return this.CONTAINER;
    }

    @Override
    public @NotNull String getKey() {
        return this.KEY;
    }

    @Override
    public PlayerFireDamageFlag createCopyOfDefault() {
        return new PlayerFireDamageFlag();
    }
}
