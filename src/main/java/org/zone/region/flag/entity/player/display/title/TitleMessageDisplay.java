package org.zone.region.flag.entity.player.display.title;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.ZonePlugin;
import org.zone.config.node.ZoneNodes;
import org.zone.config.node.title.DefaultTitleFadeInNode;
import org.zone.config.node.title.DefaultTitleFadeOutNode;
import org.zone.config.node.title.DefaultTitleStayNode;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.MessageDisplayType;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;

import java.time.Duration;
import java.util.Objects;

public class TitleMessageDisplay implements MessageDisplay {

    private @NotNull Component subTitle;
    private @Nullable Duration fadeIn;
    private @Nullable Duration stay;
    private @Nullable Duration fadeOut;

    public TitleMessageDisplay(@NotNull TitleMessageDisplayBuilder builder) {
        this.subTitle = builder.getSubTitle();
        this.fadeIn = builder.getFadeIn();
        this.stay = builder.getStay();
        this.fadeOut = builder.getFadeOut();
    }

    /**
     * Gets the subtitle of the Title Message
     *
     * @return The subtitle
     * @since 1.0.1
     */
    public @NotNull Component getSubTitle() {
        return this.subTitle;
    }

    /**
     * Gets the fade in duration of the Title message
     *
     * @return The fade in duration
     * @since 1.0.1
     */
    public @NotNull Duration getFadeIn() {
        if (this.fadeIn == null) {
            return ZonePlugin.getZonesPlugin().getConfig().getOrElse(ZoneNodes.DEFAULT_TITLE_FADE_IN).asDuration();
        }
        return this.fadeIn;
    }

    /**
     * Gets the stay duration of the Title message
     *
     * @return The stay duration
     * @since 1.0.1
     */
    public @NotNull Duration getStay() {
        if (this.stay == null) {
            return ZonePlugin.getZonesPlugin().getConfig().getOrElse(ZoneNodes.DEFAULT_TITLE_STAY).asDuration();
        }
        return this.stay;
    }

    /**
     * Gets the fade out duration of the Title message
     *
     * @return The fade out duration
     * @since 1.0.1
     */
    public @NotNull Duration getFadeOut() {
        if (this.fadeOut == null) {
            return ZonePlugin.getZonesPlugin().getConfig().getOrElse(ZoneNodes.DEFAULT_TITLE_FADE_OUT).asDuration();
        }
        return this.fadeOut;
    }

    /**
     * Sets the subtitle of the Title message
     *
     * @param subTitle The subtitle component to be set
     * @since 1.0.1
     */
    public void setSubTitle(@NotNull Component subTitle) {
        this.subTitle = subTitle;
    }


    /**
     * Sets the fade in duration of the Title message
     *
     * @param fadeIn The fade in duration to set
     * @since 1.0.1
     */
    public void setFadeIn(@Nullable Duration fadeIn) {
        this.fadeIn = Objects.requireNonNullElseGet(fadeIn, () -> new DefaultTitleFadeInNode().getInitialValue().asDuration());
    }

    /**
     * Sets the fade in duration of the Title message to default
     *
     * @since 1.0.1
     */
    public void setFadeIn() {
        this.setFadeIn(null);
    }

    /**
     * Sets the stay duration of the Title message
     *
     * @param stay The stay duration to set
     * @since 1.0.1
     */
    public void setStay(@Nullable Duration stay) {
        this.stay = Objects.requireNonNullElseGet(stay,
                () -> new DefaultTitleStayNode().getInitialValue().asDuration());
    }

    /**
     * Sets the stay duration of the Title message to default
     *
     * @since 1.0.1
     */
    public void setStay() {
        this.setStay(null);
    }

    /**
     * Sets the fade out duration of the Title message
     *
     * @param fadeOut The fade out duration to set
     * @since 1.0.1
     */
    public void setFadeOut(@Nullable Duration fadeOut) {
        this.fadeOut = Objects.requireNonNullElseGet(fadeOut,
                () -> new DefaultTitleFadeOutNode().getInitialValue().asDuration());
    }

    /**
     * Sets the fade out duration of the Title message to default
     *
     * @since 1.0.1
     */
    public void setFadeOut() {
        this.setFadeOut(null);
    }
    /**
     * Gets {@link Title.Times} for the title
     *
     * @return {@link Title.Times} for the title
     * @since 1.0.1
     */
    public @NotNull Title.Times getTimes() {
        Duration fadeIn = this.getFadeIn();
        Duration stay = this.getStay();
        Duration fadeOut = this.getFadeOut();
        return Title.Times.of(fadeIn, stay, fadeOut);
    }

    @Override
    public void sendMessage(@NotNull Component message, @NotNull Player receiver) {
        Title titleMessage = Title.title(message, this.subTitle, this.getTimes());
        receiver.showTitle(titleMessage);
    }

    @Override
    public @NotNull MessageDisplayType<? extends MessageDisplay> getType() {
        return MessageDisplayTypes.TITLE;
    }
}
