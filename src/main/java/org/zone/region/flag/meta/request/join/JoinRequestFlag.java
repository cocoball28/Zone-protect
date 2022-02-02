package org.zone.region.flag.meta.request.join;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

import java.util.*;

public class JoinRequestFlag implements Flag {

    @NotNull final  Collection<UUID> playerInvitingUUID = new HashSet<>();
    @NotNull final Collection<UUID> playerRequestingJoinUUID = new HashSet<>();

    public JoinRequestFlag(@NotNull Collection<UUID> playerRequestingJoinUUIDs,
            @NotNull Collection<UUID> playerInvitingUUIDs) {
        this.playerRequestingJoinUUID.addAll(playerRequestingJoinUUIDs);
        this.playerInvitingUUID.addAll(playerInvitingUUIDs);
    }

    public JoinRequestFlag(@NotNull Collection<UUID> playerInvitingUUIDs, @NotNull UUID... pRequestingJoinUUIDs) {
        this(Arrays.asList(pRequestingJoinUUIDs), Collections.unmodifiableCollection(playerInvitingUUIDs));
    }

    public JoinRequestFlag() {
        //empty constructor
    }

    public @NotNull Collection<UUID> getJoins() {
        return Collections.unmodifiableCollection(this.playerRequestingJoinUUID);
    }

    public @NotNull Collection<UUID> getInvites() {
        return Collections.unmodifiableCollection(this.playerInvitingUUID);
    }

    public void registerJoin(@NotNull UUID uuid) {
        this.playerRequestingJoinUUID.add(uuid);
    }

    public void registerInvites(@NotNull Collection<UUID> uuids) {
        this.playerInvitingUUID.addAll(uuids);
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.JOIN_REQUEST;
    }
}
