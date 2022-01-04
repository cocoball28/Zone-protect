package org.zone.region.flag.entity.monster.move;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

/**
 * A flag designed to block monsters from walking into the zone
 */
public class PreventMonsterFlagType implements FlagType.TaggedFlagType<PreventMonsterFlag> {

    public static final String NAME = "Prevent Monsters";
    public static final String KEY = "prevent_monsters";

    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return KEY;
    }

    @Override
    public PreventMonsterFlag createCopyOfDefault() {
        return new PreventMonsterFlag();
    }
}
