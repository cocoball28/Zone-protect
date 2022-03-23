package org.zone.region.flag.meta.invite;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class InviteFlag implements Flag.Serializable {

    private final @NotNull Collection<UUID> invites = new HashSet<>();

    public InviteFlag(@NotNull Collection<UUID> invites) {
        this.invites.addAll(invites);
    }

    public InviteFlag(@NotNull UUID... invites) {
        this(Arrays.asList(invites));
    }

    public InviteFlag() {}

    public @NotNull Collection<UUID> getInvites() {
        return this.invites;
    }

    public void registerInvites(@NotNull Collection<UUID> uuids) {
        this.invites.addAll(uuids);
    }

    @Override
    public @NotNull FlagType.SerializableType<? extends Serializable> getType() {
        return FlagTypes.INVITE;
    }
}
