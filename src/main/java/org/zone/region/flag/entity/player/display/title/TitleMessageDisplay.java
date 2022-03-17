package org.zone.region.flag.entity.player.display.title;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.MessageDisplayType;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;

public class TitleMessageDisplay implements MessageDisplay {

    private @NotNull Component subTitle;
    private @NotNull Title.Times times;

    public TitleMessageDisplay(@NotNull Component subTitle, @NotNull Title.Times times) {
        this.subTitle = subTitle;
        this.times = times;
    }

    public @NotNull Component getSubTitle() {
        return this.subTitle;
    }

    public @NotNull Title.Times getTimes() {
        return this.times;
    }

    public void setSubTitle(@NotNull Component subTitle) {
        this.subTitle = subTitle;
    }

    public void setTimes(@NotNull Title.Times times) {
        this.times = times;
    }
    @Override
    public void sendMessage(@NotNull Component message, @NotNull Player receiver) {
        Title titleMessage = Title.title(message, this.subTitle);
        receiver.showTitle(titleMessage);
    }

    @Override
    public @NotNull MessageDisplayType<? extends MessageDisplay> getType() {
        return MessageDisplayTypes.TITLE;
    }
}
