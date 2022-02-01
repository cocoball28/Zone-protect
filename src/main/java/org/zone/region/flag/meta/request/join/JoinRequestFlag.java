package org.zone.region.flag.meta.request.join;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

import java.util.*;

public class JoinRequestFlag implements Flag {

    @NotNull final Collection<UUID> pUUIDs = new HashSet<>();

    public JoinRequestFlag(@NotNull Collection<UUID> uuids) {
        this.pUUIDs.addAll(uuids);
    }

    public JoinRequestFlag(@NotNull UUID... uuid) {
        this(Arrays.asList(uuid));
    }

    public JoinRequestFlag() {
        //empty constructor
    }

    public @NotNull Collection<UUID> getInvites() {
        return Collections.unmodifiableCollection(this.pUUIDs);
    }

    public void registerInvite(@NotNull UUID uuid) {
        this.pUUIDs.add(uuid);
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.JOIN_REQUEST;
    }
}
