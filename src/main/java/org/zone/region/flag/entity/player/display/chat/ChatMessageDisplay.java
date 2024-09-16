package org.zone.region.flag.entity.player.display.chat;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.MessageDisplayType;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;

public class ChatMessageDisplay implements MessageDisplay {

    @Override
    public void sendMessage(@NotNull Component message, @NotNull Player receiver) {
        receiver.sendMessage(message);
    }

    @Override
    public @NotNull MessageDisplayType<? extends MessageDisplay> getType() {
        return MessageDisplayTypes.CHAT;
    }
}
