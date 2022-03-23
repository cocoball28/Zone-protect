package org.zone.region.flag.meta.request.join;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

import java.util.*;

public class JoinRequestFlag implements Flag.Serializable {

    private final @NotNull Collection<UUID> joinRequests = new HashSet<>();

    public JoinRequestFlag(@NotNull Collection<UUID> joinRequests) {
        this.joinRequests.addAll(joinRequests);
    }

    public JoinRequestFlag(@NotNull UUID... joinRequests) {
        this(Arrays.asList(joinRequests));
    }

    public JoinRequestFlag() {
        //empty constructor
    }

    public @NotNull Collection<UUID> getJoins() {
        return Collections.unmodifiableCollection(this.joinRequests);
    }

    public void registerJoin(@NotNull UUID uuid) {
        this.joinRequests.add(uuid);
    }

    @Override
    public @NotNull JoinRequestFlagType getType() {
        return FlagTypes.JOIN_REQUEST;
    }
}
