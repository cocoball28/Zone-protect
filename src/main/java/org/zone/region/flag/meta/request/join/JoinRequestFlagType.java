package org.zone.region.flag.meta.request.join;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JoinRequestFlagType implements FlagType<JoinRequestFlag> {

    public static final String NAME = "Join Request";
    public static final String KEY = "join_request";

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
    public @NotNull JoinRequestFlag load(@NotNull ConfigurationNode node) throws IOException {
        List<String> joinRequestUUIDsAsString = node.node("JoinRequests").getList(String.class);
        List<String> inviteUUIDsAsString = node.node("Invites").getList(String.class);
        if (joinRequestUUIDsAsString == null) {
            throw new IOException("Unknown UUID of join requests");
        }
        if (inviteUUIDsAsString == null) {
            throw new IOException("Unknown UUID of invites");
        }
        Collection<UUID> joinRequestUUIDsFromString = joinRequestUUIDsAsString
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        Collection<UUID> inviteUUIDsFromString = inviteUUIDsAsString
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        return new JoinRequestFlag(joinRequestUUIDsFromString, inviteUUIDsFromString);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable JoinRequestFlag save) throws
            IOException {
        if (save == null) {
            return;
        }

        node
                .node("JoinRequests")
                .set(save.getJoins().stream().map(UUID::toString).collect(Collectors.toList()));
        node
                .node("Invites")
                .set(save.getInvites().stream().map(UUID::toString).collect(Collectors.toList()));
    }

    @Override
    public @NotNull Optional<JoinRequestFlag> createCopyOfDefaultFlag() {
        return Optional.of(new JoinRequestFlag());
    }
}
