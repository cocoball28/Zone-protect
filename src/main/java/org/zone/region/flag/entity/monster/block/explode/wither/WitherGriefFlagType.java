package org.zone.region.flag.entity.monster.block.explode.wither;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class WitherGriefFlagType implements FlagType.TaggedFlagType<WitherGriefFlag> {
    @Override
    public @NotNull String getName() {
        return "Wither Grief";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "wither_grief";
    }

    @Override
    public WitherGriefFlag createCopyOfDefault() {
        return new WitherGriefFlag();
    }
}
