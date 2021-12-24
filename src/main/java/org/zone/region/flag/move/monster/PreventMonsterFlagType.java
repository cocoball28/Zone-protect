package org.zone.region.flag.move.monster;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

/**
 * A flag designed to block monsters from walking into the zone
 */
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
        ConfigurationNode enabledNode = node.node("Enabled");
        if (enabledNode.isNull()) {
            throw new IOException("Cannot find value 'Enabled'");
        }
        return new PreventMonsterFlag(enabledNode.getBoolean());
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable PreventMonsterFlag save) throws
            IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        if (save.getEnabled().isEmpty()) {
            node.set(null);
            return;
        }
        node.node("Enabled").set(save.isEnabled());
    }

    @Override
    public @NotNull Optional<PreventMonsterFlag> createCopyOfDefaultFlag() {
        return Optional.of(new PreventMonsterFlag(PreventMonsterFlag.DEFAULT
                                                          .getEnabled()
                                                          .orElse(null)));
    }
}
