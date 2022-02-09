package org.zone.region.flag.entity.monster.block.ignite;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class SkeletonGriefFlagType implements FlagType.TaggedFlagType<SkeletonGriefFlag> {
    @Override
    public @NotNull String getName() {
        return "Skeleton Grief";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "skeleton_grief";
    }

    @Override
    public SkeletonGriefFlag createCopyOfDefault() {
        return new SkeletonGriefFlag();
    }
}
