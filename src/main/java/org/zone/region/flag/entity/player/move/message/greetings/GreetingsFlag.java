package org.zone.region.flag.entity.player.move.message.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplay;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayTypes;

import java.util.Optional;

/**
 * Flag for greetings
 */
public class GreetingsFlag implements Flag {

    private @NotNull MessageDisplay messageDisplay = MessageDisplayTypes.CHAT_MESSAGE_DISPLAY.createCopyOfDefault();
    private @NotNull Component text;
    @Deprecated(forRemoval = true)
    private @Nullable Component oldText;

    @Deprecated(forRemoval = true)
    public GreetingsFlag() {
        this(null);
    }
    @Deprecated(forRemoval = true)
    public GreetingsFlag(@NotNull Component greetingsMessage) {
        this.oldText = greetingsMessage;
    }

    public GreetingsFlag(@NotNull Component greetingsMessage, @NotNull MessageDisplay messageDisplay) {
        this.text = greetingsMessage;
        this.messageDisplay = messageDisplay;
    }

    /**
     * Method to get the greetings message
     *
     * @return Nullable optional version of the text Component
     */
    public Optional<Component> getMessage() {
        return Optional.ofNullable(this.text);
    }

    /**
     * Method the get the display type of message
     *
     * @return The message display type
     */
    public MessageDisplay getDisplayType() {
        return this.messageDisplay;
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

