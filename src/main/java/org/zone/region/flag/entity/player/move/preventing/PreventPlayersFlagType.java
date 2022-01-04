package org.zone.region.flag.entity.player.move.preventing;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class PreventPlayersFlagType implements FlagType.TaggedFlagType<PreventPlayersFlag> {

    @Override
    public @NotNull String getName() {
        return "Prevent Players";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "prevent_players";
    }

    @Override
    public PreventPlayersFlag createCopyOfDefault() {
        return new PreventPlayersFlag();
    }
}
