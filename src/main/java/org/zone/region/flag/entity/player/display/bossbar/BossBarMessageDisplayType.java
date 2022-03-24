package org.zone.region.flag.entity.player.display.bossbar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.entity.player.display.MessageDisplayType;

import java.io.IOException;

public class BossBarMessageDisplayType implements MessageDisplayType<BossBarMessageDisplay> {

    @Override
    public @NotNull String getName() {
        return "Bossbar Display";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "bossbar_display";
    }

    @Override
    public @NotNull BossBarMessageDisplay load(@NotNull ConfigurationNode node) throws IOException {
        String displayTypeID = node.node("DisplayType").getString();
        if (displayTypeID == null) {
            throw new IOException("Couldn't get the display type");
        }
        return new BossBarMessageDisplayBuilder().build();
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable BossBarMessageDisplay save)
            throws IOException {
        if (save == null) {
            throw new IOException("Display type can't be null");
        }
        String displayTypeID = this.getId();
        node.node("DisplayType").set(displayTypeID);
    }

    @Override
    public BossBarMessageDisplay createCopyOfDefault() {
        return new BossBarMessageDisplayBuilder().build();
    }
}
