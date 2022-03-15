package org.zone.region.flag.entity.player.move.message.display.title;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplay;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayType;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayTypes;

public class TitleMessageDisplay implements MessageDisplay {

    @Override
    public void sendMessage(@NotNull Component message, @NotNull Player receiver) {
        Title titleMessage = Title.title(message, Component.text(""));
        receiver.showTitle(titleMessage);
    }

    @Override
    public @NotNull MessageDisplayType<?> getType() {
        return MessageDisplayTypes.TITLE_MESSAGE_DISPLAY;
    }
}
