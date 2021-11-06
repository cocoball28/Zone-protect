package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;

public interface Flag {

    @NotNull FlagType<?> getType();
}
