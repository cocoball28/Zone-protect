package org.zone.region.flag.entity.player.move.message.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplay;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayTypes;

public class LeavingFlag implements Flag {

    private @NotNull MessageDisplay messageDisplay = MessageDisplayTypes.CHAT_MESSAGE_DISPLAY.createCopyOfDefault();
    private @NotNull Component leavingMessage;
    @Deprecated(forRemoval = true)
    private @NotNull Component oldLeavingMessage;

    public LeavingFlag(@NotNull Component leavingMessage, @NotNull MessageDisplay messageDisplay) {
        this.leavingMessage = leavingMessage;
        this.messageDisplay = messageDisplay;
    }

    @Deprecated(forRemoval = true)
    public LeavingFlag(@NotNull Component leavingMessage) {
        this.oldLeavingMessage = leavingMessage;
    }

    public @NotNull Component getLeavingMessage() {
        return this.leavingMessage;
    }

    public @NotNull MessageDisplay getDisplayType() {
        return this.messageDisplay;
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
