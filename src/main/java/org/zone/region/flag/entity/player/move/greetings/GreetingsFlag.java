package org.zone.region.flag.entity.player.move.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;

/**
 * Flag for greetings
 */
public class GreetingsFlag implements Flag.Serializable {

    private @NotNull MessageDisplay messageDisplay = MessageDisplayTypes.CHAT.createCopyOfDefault();
    private @NotNull Component text;

    @Deprecated(forRemoval = true, since = "1.0.1")
    public GreetingsFlag(@NotNull Component greetingsMessage) {
        this.text = greetingsMessage;
    }

    public GreetingsFlag(
            @NotNull Component greetingsMessage,
            @NotNull MessageDisplay messageDisplay) {
        this.text = greetingsMessage;
        this.messageDisplay = messageDisplay;
    }

    /**
     * Method to get the greetings message
     *
     * @return The greetings message
     */
    @Deprecated(forRemoval = true, since = "1.0.1")
    public @Nullable Component getLegacyGreetingsMessage() {
        return this.text;
    }

    /**
     * Method to get the greetings message
     *
     * @return The greetings message
     */
    public @NotNull Component getGreetingsMessage() {
        return this.text;
    }

    /**
     * Method the get the display type of message
     *
     * @return The message display type
     */
    public @NotNull MessageDisplay getDisplayType() {
        return this.messageDisplay;
    }

    /**
     * Method to set the display type
     *
     * @param messageDisplay The display type to set
     */
    public void setDisplayType(@NotNull MessageDisplay messageDisplay) {
        this.messageDisplay = messageDisplay;
    }

    /**
     * Method to set the greetings message
     *
     * @deprecated Since 1.0.1
     *
     * @param component The component to be set
     */
    @Deprecated(forRemoval = true, since = "1.0.1")
    public void setLegacyMessage(@NotNull Component component) {
        this.text = component;
    }

    /**
     * Method to set the greetings message
     *
     * @param component The component to be set
     */
    public void setMessage(@NotNull Component component) {
        this.text = component;
    }

    @Override
    public @NotNull GreetingsFlagType getType() {
        return FlagTypes.GREETINGS;
    }

}

