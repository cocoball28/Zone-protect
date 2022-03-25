package org.zone.commands.system.arguments.operation;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Gets the remaining arguments left in the command. This must include at least one argument
 * @param <T> The type the remaining should be
 * @since 1.0.0
 */
public class RemainingArgument<T> implements CommandArgument<List<T>> {

    private final String id;
    private final List<CommandArgument<T>> argument;

    /**
     * Do not use, only here to prevent 0 arguments entered to the varArg
     * @param id ignored
     * @deprecated A command argument is required
     */
    @Deprecated
    public RemainingArgument(String id) {
        throw new RuntimeException("Remaining Argument requires at least 1 argument");
    }

    /**
     * Used for if you wish to gain the remaining arguments of a single type
     * @param argument The single argument to check for each remaining argument
     * @since 1.0.0
     */
    public RemainingArgument(CommandArgument<T> argument) {
        this(argument.getId(), argument);
    }

    /**
     * Used for if there are multiple possibilities for the remaining argument
     * @param id The id of the argument -> the arguments in the next parameter, the ID is
     *           ignored, so enter anything you wish
     * @param argument The arguments to check -> this is checked in the order they are passed in
     * @since 1.0.0
     */
    @SafeVarargs
    public RemainingArgument(String id, CommandArgument<T>... argument) {
        this(id, Arrays.asList(argument));
    }

    /**
     * Used for if there are multiple possibilities for the remaining argument
     * @param id The id of the argument -> the arguments in the next parameter, the ID is
     *           ignored, so enter anything you wish
     * @param argument The arguments to check
     * @since 1.0.0
     */
    public RemainingArgument(String id, Collection<CommandArgument<T>> argument) {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("Remaining Argument cannot have a argument of empty");
        }
        this.id = id;
        this.argument = new ArrayList<>(argument);
    }

    private CommandArgumentResult<T> parseAny(
            ArgumentCommand command, CommandContext context, int argumentPos) throws IOException {
        IOException e1 = null;
        for (int commandIndex = 0; commandIndex < this.argument.size(); commandIndex++) {
            try {
                CommandArgumentContext<T> argumentContext = new CommandArgumentContext<>(command,
                        this.argument.get(commandIndex),
                        argumentPos,
                        context.getCommand());
                return this.argument.get(commandIndex).parse(context, argumentContext);
            } catch (IOException e) {
                if (commandIndex == 0) {
                    e1 = e;
                }
            }
        }
        if (e1 == null) {
            //shouldn't be possible
            throw new IOException("Unknown error occurred");
        }
        throw e1;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<List<T>> parse(
            CommandContext context, CommandArgumentContext<List<T>> argument) throws IOException {
        int currentArgumentPos = argument.getFirstArgument();
        List<T> list = new ArrayList<>();
        while (currentArgumentPos < context.getCommand().length) {
            CommandArgumentResult<T> entry = this.parseAny(argument.getArgumentCommand(),
                    context,
                    currentArgumentPos);
            currentArgumentPos = entry.position();
            list.add(entry.value());
        }
        return new CommandArgumentResult<>(currentArgumentPos, list);
    }

    @Override
    public @NotNull Set<CommandCompletion> suggest(
            @NotNull CommandContext commandContext, @NotNull CommandArgumentContext<List<T>> argument) {
        int currentArgPlacement = argument.getFirstArgument();
        while (currentArgPlacement < commandContext.getCommand().length) {
            final int finalCurrentArgPlacement = currentArgPlacement;
            CommandArgumentResult<T> entry;
            try {
                entry = this.parseAny(argument.getArgumentCommand(), commandContext, currentArgPlacement);
            } catch (IOException e) {
                return this.argument
                        .stream()
                        .flatMap(a -> a
                                .suggest(commandContext,
                                        new CommandArgumentContext<>(argument.getArgumentCommand(),
                                                a,
                                                finalCurrentArgPlacement,
                                                commandContext.getCommand()))
                                .stream())
                        .collect(Collectors.toSet());
            }
            currentArgPlacement = entry.position();
        }
        return Collections.emptySet();
    }
}