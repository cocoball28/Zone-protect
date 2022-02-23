package org.zone.region.flag.entity.monster.block.explode.creeper;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class CreeperGriefFlagType implements FlagType.TaggedFlagType<CreeperGriefFlag> {
    @Override
    public @NotNull String getName() {
        return "Creeper Explosion";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "creeper_explosion";
    }

    @Override
    public CreeperGriefFlag createCopyOfDefault() {
        return new CreeperGriefFlag();
    }
}
