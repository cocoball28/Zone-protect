package org.zone.region.flag.entity.player.move.leaving;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Serializable;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.MessageDisplayType;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;

import java.io.IOException;
import java.util.Optional;

public class LeavingFlagType implements FlagType.SerializableType<LeavingFlag> {

    public static final String NAME = "Leaving";
    public static final String KEY = "leaving";

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
    public @NotNull LeavingFlag load(@NotNull ConfigurationNode node) throws IOException {
        String message = node.node("Message").getString();
        String displayTypeID = node.node("DisplayType").getString();
        MessageDisplayType<?> displayTypeAvailable;
        if (message == null) {
            throw new IOException("Cannot read message");
        }
        if (displayTypeID == null) {
            displayTypeAvailable = MessageDisplayTypes.CHAT;
        } else {
            displayTypeAvailable = ZonePlugin
                    .getZonesPlugin()
                    .getMessageDisplayManager()
                    .getRegistered()
                    .stream()
                    .filter(messageDisplayType -> messageDisplayType.getId().equals(displayTypeID))
                    .findAny()
                    .orElseThrow(() -> new IOException("Display ID not found!"));
        }
        MessageDisplay messageDisplay = displayTypeAvailable.load(node);
        Component component = GsonComponentSerializer.gson().deserialize(message);
        return new LeavingFlag(component, messageDisplay);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable LeavingFlag save) throws
            IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        node.node("DisplayType").set(save.getDisplayType().getType().getId());
        this.saveDisplay(node, save.getDisplayType());
        String message = GsonComponentSerializer.gson().serialize(save.getLeavingMessage());
        node.node("Message").set(message);
    }

    public <T extends MessageDisplay> void saveDisplay(ConfigurationNode node, T displayType) throws
            IOException {
        Serializable<T> type = (Serializable<T>) displayType.getType();
        type.save(node, displayType);
    }

    @Override
    public @NotNull Optional<LeavingFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
