package org.zone.region.flag.entity.monster.block.knock;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class ZombieGriefFlagType implements FlagType.TaggedFlagType<ZombieGriefFlag> {
    @Override
    public @NotNull String getName() {
        return "Zombie Grief";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "zombie_grief";
    }

    @Override
    public ZombieGriefFlag createCopyOfDefault() {
        return new ZombieGriefFlag();
    }
}
