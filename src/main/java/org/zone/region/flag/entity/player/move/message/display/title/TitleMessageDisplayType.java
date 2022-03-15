package org.zone.region.flag.entity.player.move.message.display.title;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayType;
import org.zone.utils.Messages;

import java.io.IOException;

public class TitleMessageDisplayType implements MessageDisplayType<TitleMessageDisplay> {
    @Override
    public @NotNull String getName() {
        return "Title Display";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "title_display";
    }

    @Override
    public @NotNull TitleMessageDisplay load(@NotNull ConfigurationNode node)
            throws IOException {
        String displayTypeID = node.node("DisplayType").getString();
        if (displayTypeID == null) {
            throw new IOException("Couldn't get the display type");
        }
        return new TitleMessageDisplay();
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable TitleMessageDisplay save)
            throws IOException {
        if (save == null) {
            throw new IOException("Display type can't be null");
        }
        String displayTypeID = this.getId();
        node.node("DisplayType").set(displayTypeID);
    }

    @Override
    public TitleMessageDisplay createCopyOfDefault() {
        return new TitleMessageDisplay();
    }
}
