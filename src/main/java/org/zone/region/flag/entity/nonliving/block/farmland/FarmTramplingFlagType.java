package org.zone.region.flag.entity.nonliving.block.farmland;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class FarmTramplingFlagType implements FlagType.TaggedFlagType<FarmTramplingFlag> {

    @Override
    public @NotNull String getName() {
        return "Farm Trample";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "farm_trample";
    }

    @Override
    public FarmTramplingFlag createCopyOfDefault() {
        return new FarmTramplingFlag();
    }
}
