package org.zone.commands.system.arguments.simple;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.util.Collections;
import java.util.Set;

/**
 * Gets a string value from a command
 *
 * @since 1.0.0
 */
public class StringArgument implements CommandArgument<String> {

    private final @NotNull String id;

    /**
     * Creates a string argument
     *
     * @param id The id of the argument
     * @since 1.0.0
     */
    public StringArgument(@NotNull String id) {
        this.id = id;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<String> parse(
            CommandContext context, CommandArgumentContext<String> argument) {
        String text = context.getCommand()[argument.getFirstArgument()];
        return CommandArgumentResult.from(argument, text);

    }

    @Override
    public @NotNull Set<CommandCompletion> suggest(
            @NotNull CommandContext commandContext,
            @NotNull CommandArgumentContext<String> argument) {
        return Collections.emptySet();
    }
}
