package org.zone.region.flag.meta.invite;

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

public class InviteFlagType implements FlagType.SerializableType<InviteFlag> {

    @Override
    public @NotNull String getName() {
        return "Invite";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "invite";
    }

    @Override
    public @NotNull InviteFlag load(@NotNull ConfigurationNode node) throws IOException {
        List<String> inviteUUIDsAsString = node.node("Invites").getList(String.class);
        if (inviteUUIDsAsString == null) {
            throw new IOException("Unknown UUIDs of invites");
        }
        Collection<UUID> invites = inviteUUIDsAsString
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        return new InviteFlag(invites);
    }

    @Override
    public void save(
            @NotNull ConfigurationNode node, @Nullable InviteFlag save) throws IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        node
                .node("Invites")
                .set(save.getInvites().stream().map(UUID::toString).collect(Collectors.toList()));
    }

    @Override
    public @NotNull Optional<InviteFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
