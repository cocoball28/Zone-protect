package org.zone.region.flag.entity.player.display.title;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class TitleMessageDisplayBuilder {

    private Component subTitle;
    private Duration fadeIn;
    private Duration stay;
    private Duration fadeOut;

    /**
     * Gets the subtitle of the {@link net.kyori.adventure.title.Title}
     *
     * @return The subtitle
     * @since 1.0.1
     */
    public Component getSubTitle() {
        return this.subTitle;
    }

    /**
     * Gets the fade in duration for the {@link net.kyori.adventure.title.Title}
     *
     * @return The duration of fade in
     * @since 1.0.1
     */
    public Duration getFadeIn() {
        return this.fadeIn;
    }

    /**
     * Gets the stay duration for the {@link net.kyori.adventure.title.Title}
     *
     * @return The duration of stay
     * @since 1.0.1
     */
    public Duration getStay() {
        return this.stay;
    }

    /**
     * Gets the fade in duration for the {@link net.kyori.adventure.title.Title}
     *
     * @return The duration of fade out
     * @since 1.0.1
     */
    public Duration getFadeOut() {
        return this.fadeOut;
    }

    /**
     * Sets the subtitle for the {@link net.kyori.adventure.title.Title}
     *
     * @param subTitle The subtitle to be set
     * @return {@link TitleMessageDisplayBuilder}
     * @since 1.0.1
     */
    public @NotNull TitleMessageDisplayBuilder setSubTitle(@NotNull Component subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    /**
     * Sets the fade in duration for the {@link net.kyori.adventure.title.Title}
     *
     * @param fadeIn The fade in duration to be set
     * @return {@link TitleMessageDisplayBuilder}
     * @since 1.0.1
     */
    public @NotNull TitleMessageDisplayBuilder setFadeIn(Duration fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    /**
     * Sets the stay duration for the {@link net.kyori.adventure.title.Title}
     *
     * @param stay The stay duration to be set
     * @return {@link TitleMessageDisplayBuilder}
     * @since 1.0.1
     */
    public @NotNull TitleMessageDisplayBuilder setStay(Duration stay) {
        this.stay = stay;
        return this;
    }

    /**
     * Sets the fade out duration for the {@link net.kyori.adventure.title.Title}
     *
     * @param fadeOut The fade out duration to be set
     * @return {@link TitleMessageDisplayBuilder}
     * @since 1.0.1
     */
    public @NotNull TitleMessageDisplayBuilder setFadeOut(Duration fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    /**
     * The build method which builds the {@link TitleMessageDisplay} class with the desired
     * settings
     *
     * @return {@link TitleMessageDisplay} object with the builder being this
     * @since 1.0.1
     */
    public @NotNull TitleMessageDisplay build() {
        return new TitleMessageDisplay(this);
    }

}
