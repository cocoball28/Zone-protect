package org.zone.region.flag.meta.request.join;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

import java.util.*;

public class JoinRequestFlag implements Flag {

    @NotNull
    private final Collection<UUID> inviteRequests = new HashSet<>();

    @NotNull
    private final Collection<UUID> joinRequests = new HashSet<>();

    public JoinRequestFlag(
            @NotNull Collection<UUID> joinRequests,
            @NotNull Collection<UUID> inviteRequests) {
        this.joinRequests.addAll(joinRequests);
        this.inviteRequests.addAll(inviteRequests);
    }

    public JoinRequestFlag(
            @NotNull Collection<UUID> invites, @NotNull UUID... joinRequests) {
        this(Arrays.asList(joinRequests),
                Collections.unmodifiableCollection(invites));
    }

    public JoinRequestFlag() {
        //empty constructor
    }

    public @NotNull Collection<UUID> getJoins() {
        return Collections.unmodifiableCollection(this.joinRequests);
    }

    public @NotNull Collection<UUID> getInvites() {
        return Collections.unmodifiableCollection(this.inviteRequests);
    }

    public void registerJoin(@NotNull UUID uuid) {
        this.joinRequests.add(uuid);
    }

    public void registerInvites(@NotNull Collection<UUID> uuids) {
        this.inviteRequests.addAll(uuids);
    }

    @Override
    public @NotNull JoinRequestFlagType getType() {
        return FlagTypes.JOIN_REQUEST;
    }
}
