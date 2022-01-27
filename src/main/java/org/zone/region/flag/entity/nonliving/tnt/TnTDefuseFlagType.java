package org.zone.region.flag.entity.nonliving.tnt;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class TnTDefuseFlagType implements FlagType.TaggedFlagType<TnTDefuseFlag> {
    @Override
    public @NotNull String getName() {
        return "TnT Defuse";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "defuse_tnt";
    }

    @Override
    public TnTDefuseFlag createCopyOfDefault() {
        return new TnTDefuseFlag();
    }
}
