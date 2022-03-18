package org.zone.region.flag.meta.service.ban;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class TemporaryBanInfo implements BanInfo {

    public final @NotNull LocalDateTime releaseDateTime;
    public final @NotNull UUID uuid;

    public TemporaryBanInfo(@NotNull UUID uuid, @NotNull LocalDateTime releaseDateTime) {
        this.uuid = uuid;
        this.releaseDateTime = releaseDateTime;
    }

    @Override
    public @NotNull UUID getId() {
        return this.uuid;
    }

    public @NotNull LocalDateTime getReleaseTime() {
        return this.releaseDateTime;
    }

}
