package org.zone.region.flag.entity.player.move.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

import java.util.Optional;

/**
 * Flag for greetings
 */
public class GreetingsFlag implements Flag.Serializable {

    private @Nullable Component text;

    public GreetingsFlag() {
        this(null);
    }

    public GreetingsFlag(@Nullable Component component) {
        this.text = component;
    }

    public Optional<Component> getMessage() {
        return Optional.ofNullable(this.text);
    }

    public void setMessage(@Nullable Component component) {
        this.text = component;
    }

    @Override
    public @NotNull GreetingsFlagType getType() {
        return FlagTypes.GREETINGS;
    }

}

