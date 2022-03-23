package org.zone.region.flag.entity.player.display.title;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class TitleMessageDisplayBuilder {

    private Component subTitle;
    private Duration fadeIn;
    private Duration stay;
    private Duration fadeOut;

    public Component getSubTitle() {
        return this.subTitle;
    }

    public Duration getFadeIn() {
        return this.fadeIn;
    }

    public Duration getStay() {
        return this.stay;
    }

    public Duration getFadeOut() {
        return this.fadeOut;
    }

    public TitleMessageDisplayBuilder setSubTitle(@NotNull Component subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public TitleMessageDisplayBuilder setFadeIn(Duration fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public TitleMessageDisplayBuilder setStay(Duration stay) {
        this.stay = stay;
        return this;
    }

    public TitleMessageDisplayBuilder setFadeOut(Duration fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public @NotNull TitleMessageDisplay build() {
        return new TitleMessageDisplay(this);
    }

}
