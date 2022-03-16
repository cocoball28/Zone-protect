package org.zone.region.flag.entity.player.move.message.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplay;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayTypes;

/**
 * Flag for greetings
 */
public class GreetingsFlag implements Flag {

    private @NotNull MessageDisplay messageDisplay = MessageDisplayTypes.CHAT.createCopyOfDefault();
    private @NotNull Component text;
    @Deprecated(forRemoval = true)
    private @Nullable Component legacyText;

    @Deprecated(forRemoval = true)
    public GreetingsFlag() {
        this(null);
    }
    @Deprecated(forRemoval = true)
    public GreetingsFlag(@Nullable Component greetingsMessage) {
        this.legacyText = greetingsMessage;
    }

    public GreetingsFlag(@NotNull Component greetingsMessage, @NotNull MessageDisplay messageDisplay) {
        this.text = greetingsMessage;
        this.messageDisplay = messageDisplay;
    }

    @Deprecated(forRemoval = true)
    /**
     * Method to get the greetings message
     *
     * @return The greetings message
     */
    public Component getLegacyGreetingsMessage() {
        return this.text;
    }

    /**
     * Method to get the greetings message
     *
     * @return The greetings message
     */
    public Component getGreetingsMessage() {
        return this.text;
    }

    /**
     * Method the get the display type of message
     *
     * @return The message display type
     */
    public MessageDisplay getDisplayType() {
        return this.messageDisplay;
    }

    @Deprecated(forRemoval = true)
    /**
     * Method to set the greetings message
     *
     * @deprecated since 1.0.1
     *
     * @param component The component to be set
     */
    public void setLegacyMessage(@NotNull Component component) {
        this.legacyText = component;
    }

    /**
     * Method to set the greetings message
     *
     * @param component The component to be set
     */
    public void setMessage(@NotNull Component component) {
        this.text = component;
    }

    /**
     * Method to set the display type
     *
     * @param messageDisplay The display type to set
     */
    public void setDisplayType(@NotNull MessageDisplay messageDisplay) {
        this.messageDisplay = messageDisplay;
    }

    @Override
    public @NotNull GreetingsFlagType getType() {
        return FlagTypes.GREETINGS;
    }

}

