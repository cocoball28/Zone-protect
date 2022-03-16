package org.zone.region.flag.entity.player.move.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;

public class LeavingFlag implements Flag {

    private @NotNull MessageDisplay messageDisplay = MessageDisplayTypes.CHAT.createCopyOfDefault();
    private @NotNull Component leavingMessage;
    @Deprecated(since = "1.0.1",forRemoval = true)
    private @Nullable Component legacyLeavingMessage;

    public LeavingFlag(@NotNull Component leavingMessage, @NotNull MessageDisplay messageDisplay) {
        this.leavingMessage = leavingMessage;
        this.messageDisplay = messageDisplay;
    }

    @Deprecated(since = "1.0.1",forRemoval = true)
    public LeavingFlag(@NotNull Component leavingMessage) {
        this.legacyLeavingMessage = leavingMessage;
    }

    @Deprecated(since = "1.0.1",forRemoval = true)
    public @Nullable Component getLegacyLeavingMessage() {
        return this.legacyLeavingMessage;
    }

    public @NotNull Component getLeavingMessage() {
        return this.leavingMessage;
    }

    public @NotNull MessageDisplay getDisplayType() {
        return this.messageDisplay;
    }

    @Deprecated(since = "1.0.1",forRemoval = true)
    public void setLegacyLeavingMessage(@NotNull Component leavingMessage) {
        this.legacyLeavingMessage = leavingMessage;
    }

    public void setLeavingMessage(@NotNull Component leavingMessage) {
        this.leavingMessage = leavingMessage;
    }

    public void setDisplayType(@NotNull MessageDisplay displayType) {
        this.messageDisplay = displayType;
    }

    @Override
    public @NotNull LeavingFlagType getType() {
        return FlagTypes.LEAVING;
    }
}
