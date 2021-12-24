package org.zone.region.flag.move.player.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class LeavingFlag implements Flag {

    private @NotNull Component leavingMessage;

    public LeavingFlag(@NotNull Component leavingMessage) {
        this.leavingMessage = leavingMessage;
    }

    public @NotNull Component getLeavingMessage() {
        return this.leavingMessage;
    }

    public void setLeavingMessage(@NotNull Component leavingMessage) {
        this.leavingMessage = leavingMessage;
    }

    @Override
    public @NotNull LeavingFlagType getType() {
        return FlagTypes.LEAVING;
    }
}
