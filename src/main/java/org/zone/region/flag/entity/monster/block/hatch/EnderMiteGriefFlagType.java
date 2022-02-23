package org.zone.region.flag.entity.monster.block.hatch;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class EnderMiteGriefFlagType implements FlagType.TaggedFlagType<EnderMiteGriefFlag> {
    @Override
    public @NotNull String getName() {
        return "EnderMite Grief";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "endermite_grief";
    }

    @Override
    public EnderMiteGriefFlag createCopyOfDefault() {
        return new EnderMiteGriefFlag();
    }
}
