package org.zone.region.flag.entity.player.display.title;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.entity.player.display.MessageDisplayType;

import java.io.IOException;
import java.time.Duration;

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
    public @NotNull TitleMessageDisplay load(@NotNull ConfigurationNode node) throws IOException {
        String displayTypeID = node.node("DisplayType").getString();
        if (displayTypeID == null) {
            throw new IOException("Couldn't get the display type");
        }
        return new TitleMessageDisplay(Component.empty(),
                Title.Times.of(Duration.ZERO, Duration.ofSeconds(10), Duration.ofSeconds(5)));
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable TitleMessageDisplay save) throws
            IOException {
        if (save == null) {
            throw new IOException("Display type can't be null");
        }
        String displayTypeID = this.getId();
        node.node("DisplayType").set(displayTypeID);
    }

    @Override
    public TitleMessageDisplay createCopyOfDefault() {
        return new TitleMessageDisplay(Component.empty(),
                Title.Times.of(Duration.ZERO, Duration.ofSeconds(10), Duration.ofSeconds(5)));
    }
}
