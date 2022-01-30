package org.zone.region.flag.meta.join.request;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class JoinRequestFlagType implements FlagType<JoinRequestFlag> {

    public static final String NAME = "Join Request";
    public static final PluginContainer PLUGIN = ZonePlugin.getZonesPlugin().getPluginContainer();
    public static final String KEY = "join_request";

    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return PLUGIN;
    }

    @Override
    public @NotNull String getKey() {
        return KEY;
    }

    @Override
    public @NotNull JoinRequestFlag load(@NotNull ConfigurationNode node)
            throws IOException {
        try {
            String joinRequests = node.node("JoinRequests").getString();
            if (joinRequests != null) {

                JoinRequestFlag joinRequestFlag = new JoinRequestFlag();
            }
        }
        return null;
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable JoinRequestFlag save)
            throws IOException {
        if (save == null) {
            return;
        }

        node.node("JoinRequests").set(save.pUUIDs.stream().map(UUID::toString));
    }

    @Override
    public @NotNull Optional<JoinRequestFlag> createCopyOfDefaultFlag() {
        return Optional.of(new JoinRequestFlag());
    }
}
