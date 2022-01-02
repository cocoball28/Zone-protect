package org.zone.region.flag.player.entitydamage;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class EntityDamagePlayerFlagType implements FlagType.TaggedFlagType<EntityDamagePlayerFlag> {

    @Override
    public @NotNull String getName() {
        return "Damage Flag";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "damage_flag";
    }

    @Override
    public EntityDamagePlayerFlag createCopyOfDefault() {
        return new EntityDamagePlayerFlag();
    }
}