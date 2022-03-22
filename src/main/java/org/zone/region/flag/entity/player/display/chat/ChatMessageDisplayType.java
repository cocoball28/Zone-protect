package org.zone.region.flag.entity.player.display.chat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.entity.player.display.MessageDisplayType;

import java.io.IOException;

public class ChatMessageDisplayType implements MessageDisplayType<ChatMessageDisplay> {

    @Override
    public @NotNull String getName() {
        return "Chat Display";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "chat_display";
    }

    @Override
    public @NotNull ChatMessageDisplay load(@NotNull ConfigurationNode node) throws IOException {
        String displayTypeID = node.node("DisplayType").getString();
        if (displayTypeID == null) {
            throw new IOException("Couldn't get the display type");
        }
        return new ChatMessageDisplay();
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable ChatMessageDisplay save) throws
            IOException {
        if (save == null) {
            throw new IOException("Display type can't be null");
        }
        String displayTypeSaveID = this.getId();
        node.node("DisplayType").set(displayTypeSaveID);
    }

    @Override
    public ChatMessageDisplay createCopyOfDefault() {
        return new ChatMessageDisplay();
    }

}
