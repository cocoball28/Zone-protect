package org.zone.commands.system;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.util.Collection;
import java.util.Optional;

public interface SuggestCommandArgument<T> {
    Collection<CommandCompletion> suggest(CommandContext commandContext,
                                          CommandArgumentContext<T> argument);

    default Optional<Component> errorMessage(CommandContext context,
                                             CommandArgumentContext<T> argument) {
        return Optional.empty();
    }

}
