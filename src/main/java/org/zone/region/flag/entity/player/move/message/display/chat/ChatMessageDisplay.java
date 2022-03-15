package org.zone.region.flag.entity.player.move.message.display.chat;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplay;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayType;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayTypes;

public class ChatMessageDisplay implements MessageDisplay {

    @Override
    public void sendMessage(@NotNull Component message, @NotNull Player receiver) {
        receiver.sendMessage(message);
    }

    @Override
    public @NotNull MessageDisplayType<?> getType() {
        return MessageDisplayTypes.CHAT_MESSAGE_DISPLAY;
    }
}
