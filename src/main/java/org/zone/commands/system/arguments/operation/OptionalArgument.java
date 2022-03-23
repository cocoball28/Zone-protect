package org.zone.commands.system.arguments.operation;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.ParseCommandArgument;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class OptionalArgument<T> implements CommandArgument<T> {

    private final boolean blockSuggestions;
    private final @NotNull CommandArgument<T> arg;
    private final @NotNull ParseCommandArgument<T> value;

    /**
     * Used to wrap a single fixed value into a ParseCommandArgument
     *
     * @param <T> The type of the fixed value
     */
    public static class WrappedParser<T> implements ParseCommandArgument<T> {

        private final @NotNull T value;

        public WrappedParser(@NotNull T value) {
            this.value = value;
        }

        @Override
        public CommandArgumentResult<T> parse(
                @NotNull CommandContext context, @NotNull CommandArgumentContext<T> argument) {
            return CommandArgumentResult.from(argument, 0, this.value);
        }
    }

    public OptionalArgument(CommandArgument<T> arg, T value, boolean blockSuggestions) {
        this(arg, new WrappedParser<>(value), blockSuggestions);
    }

    public OptionalArgument(CommandArgument<T> arg, ParseCommandArgument<T> value,
            boolean blockSuggestions) {
        this.arg = arg;
        this.value = value;
        this.blockSuggestions = blockSuggestions;
    }

    /**
     * Creates the argument
     *
     * @param arg   The argument to attempt
     * @param value The fixed value to use if the argument fails
     */
    public OptionalArgument(@NotNull CommandArgument<T> arg, @NotNull T value) {
        this(arg, new WrappedParser<>(value));
    }

    /**
     * Creates the argument
     *
     * @param arg   The argument to attempt
     * @param value The value to use if the argument fails
     */
    public OptionalArgument(CommandArgument<T> arg, ParseCommandArgument<T> value) {
        this(arg, value, false);
    }

    /**
     * Gets the argument to attempt
     *
     * @return The argument to attempt to use
     */
    public CommandArgument<T> getOriginalArgument() {
        return this.arg;
    }

    @Override
    public @NotNull String getId() {
        return this.arg.getId();
    }

    @Override
    public @NotNull String getUsage() {
        String original = this.getOriginalArgument().getUsage();
        return "[" + original.substring(1, original.length() - 1) + "]";
    }

    @Override
    public @NotNull CommandArgumentResult<T> parse(
            CommandContext context, CommandArgumentContext<T> argument) throws IOException {
        if (context.getCommand().length == argument.getFirstArgument()) {
            return CommandArgumentResult.from(argument,
                    0,
                    this.value.parse(context, argument).value());
        }
        try {
            return this.arg.parse(context, argument);
        } catch (IOException e) {
            return CommandArgumentResult.from(argument,
                    0,
                    this.value.parse(context, argument).value());
        }
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext, @NotNull CommandArgumentContext<T> argument) {
        if (this.blockSuggestions) {
            return Collections.emptySet();
        }
        return this.arg.suggest(commandContext, argument);
    }

    /**
     * Creates a traditional optional argument whereby if the user does not enter a value, the
     * result is {@link Optional#empty()}
     *
     * @param argument The argument to attempt
     * @param <T>      The expected value type
     *
     * @return The optional argument
     */
    public static <T> OptionalArgument<Optional<T>> createArgument(CommandArgument<? extends T> argument) {
        return new OptionalArgument<>(new MappedArgument<>(argument, Optional::of),
                Optional.empty());
    }
}