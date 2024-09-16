package org.zone.region.flag.entity.player.move.greetings;

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

public class GreetingsFlagType implements FlagType.SerializableType<GreetingsFlag> {

    @Override
    public @NotNull String getName() {
        return "Greetings";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "greetings";
    }

    @Override
    public @NotNull GreetingsFlag load(@NotNull ConfigurationNode node) throws IOException {
        String message = node.node("Message").getString();
        String displayTypeID = node.node("DisplayType").getString();
        MessageDisplayType<?> displayTypeAvailable;
        if (message == null) {
            throw new IOException("Could not get message");
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
        MessageDisplay load = displayTypeAvailable.load(node);
        Component component = GsonComponentSerializer.gson().deserialize(message);
        return new GreetingsFlag(component, load);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable GreetingsFlag save) throws
            IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        node.node("DisplayType").set(save.getDisplayType().getType().getId());
        this.saveDisplay(node, save.getDisplayType());
        Component message = save.getGreetingsMessage();
        String componentAsString = GsonComponentSerializer.gson().serialize(message);
        node.node("Message").set(componentAsString);
    }

    public <T extends MessageDisplay> void saveDisplay(ConfigurationNode node, T displayType) throws
            IOException {
        Serializable<T> type = (Serializable<T>) displayType.getType();
        type.save(node, displayType);
    }

    @Override
    public @NotNull Optional<GreetingsFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}