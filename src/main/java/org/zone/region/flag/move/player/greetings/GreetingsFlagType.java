package org.zone.region.flag.move.player.greetings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

public class GreetingsFlagType implements FlagType<GreetingsFlag> {
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
        if (message == null) {
            throw new IOException("Couldn't get message");
        }
        Component component = GsonComponentSerializer.gson().deserialize(message);
        return new GreetingsFlag(component);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable GreetingsFlag save) throws
            IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        Optional<Component> opMessage = save.getMessage();
        if (opMessage.isEmpty()) {
            throw new IOException("Couldn't get message");
        }
        String componentAsString = GsonComponentSerializer.gson().serialize(opMessage.get());
        node.node("Message").set(componentAsString);
    }

    @Override
    public @NotNull Optional<GreetingsFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}