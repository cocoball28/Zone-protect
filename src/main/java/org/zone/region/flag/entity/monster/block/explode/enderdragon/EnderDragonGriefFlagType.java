package org.zone.region.flag.entity.monster.block.explode.enderdragon;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class EnderDragonGriefFlagType implements FlagType.TaggedFlagType<EnderDragonGriefFlag> {
    @Override
    public @NotNull String getName() {
        return "Ender Dragon Grief";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "ender_dragon_grief";
    }

    @Override
    public EnderDragonGriefFlag createCopyOfDefault() {
        return new EnderDragonGriefFlag();
    }
}
