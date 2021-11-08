package org.zone.region.flag.move.monster;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

public class PreventMonsterFlagType implements FlagType<PreventMonsterFlag> {

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
    public @NotNull PreventMonsterFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean enabled = node.node("Enabled").getBoolean();
        if (enabled) {
            return new PreventMonsterFlag();
        }
        throw new IOException("Flag has not been enabled or is missing. - Found flag in file however found it was not" +
                " enabled or missing");
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @NotNull PreventMonsterFlag save) throws IOException {
        node.node("Enabled").set(true);
    }

    @Override
    public boolean canApply(Zone zone) {
        return true;
    }

    @Override
    public Optional<PreventMonsterFlag> createDefaultFlag() {
        return Optional.of(PreventMonsterFlag.DEFAULT);
    }
}
