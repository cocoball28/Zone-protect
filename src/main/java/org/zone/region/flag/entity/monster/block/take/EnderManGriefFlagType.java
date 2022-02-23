package org.zone.region.flag.entity.monster.block.take;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class EnderManGriefFlagType implements FlagType.TaggedFlagType<EnderManGriefFlag> {
    @Override
    public @NotNull String getName() {
        return "EnderMan Grief";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "ender_man_grief";
    }

    @Override
    public EnderManGriefFlag createCopyOfDefault() {
        return new EnderManGriefFlag();
    }
}
