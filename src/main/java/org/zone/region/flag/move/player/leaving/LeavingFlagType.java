package org.zone.region.flag.move.player.leaving;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

public class LeavingFlagType implements FlagType<LeavingFlag> {

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
        if(message == null){
            throw new IOException("Cannot read message");
        }
        Component component = GsonComponentSerializer.gson().deserialize(message);
        return new LeavingFlag(component);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable LeavingFlag save) throws
            IOException {
        if(save == null){
            node.set(null);
            return;
        }
        String message = GsonComponentSerializer.gson().serialize(save.getLeavingMessage());
        node.node("Message").set(message);
    }

    @Override
    public boolean canApply(Zone zone) {
        return true;
    }

    @Override
    public Optional<LeavingFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
