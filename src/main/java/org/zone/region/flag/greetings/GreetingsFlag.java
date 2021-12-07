package org.zone.region.flag.greetings;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

import java.util.Optional;

public class GreetingsFlag implements Flag {

    private @Nullable Component text;

    public GreetingsFlag(){
        this(null);
    }

    public GreetingsFlag(@Nullable Component component){
        this.text = component;
    }

    public Optional<Component> getMessage(){
        return Optional.ofNullable(this.text);
    }

    public void setMessage(@Nullable Component component){
        this.text = component;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.GREETINGS_FLAG_TYPE;
    }

}

