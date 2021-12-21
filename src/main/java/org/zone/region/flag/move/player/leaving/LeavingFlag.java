package org.zone.region.flag.move.player.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;

public class LeavingFlag implements Flag {

    private final @NotNull Component leavingMessage;

    public LeavingFlag(@NotNull Component leavingMessage) {
        this.leavingMessage = leavingMessage;
    }

    public @NotNull Component getLeavingMessage() {
        return this.leavingMessage;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return null;
    }
}
