package org.zone.region.flag.meta.service.ban;

import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class PermanentBanInfo implements BanInfo {

    private final @NotNull UUID uuid;

    public PermanentBanInfo(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public @NotNull UUID getId() {
        return this.uuid;
    }
}
