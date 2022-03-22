package org.zone.commands.system.arguments.operation;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;
import org.zone.utils.lamda.ThrowFunction;

import java.io.IOException;
import java.util.Collection;

public class MappedArgument<O, T> implements CommandArgument<T> {

    private final @NotNull CommandArgument<O> argument;
    private final @NotNull ThrowFunction<? super O, ? extends T> function;

    public MappedArgument(
            @NotNull CommandArgument<O> argument,
            @NotNull ThrowFunction<? super O, ? extends T> function) {
        this.argument = argument;
        this.function = function;
    }

    @Override
    public @NotNull String getId() {
        return this.argument.getId();
    }

    @Override
    public CommandArgumentResult<T> parse(
            @NotNull CommandContext context, @NotNull CommandArgumentContext<T> argument) throws
            IOException {
        CommandArgumentResult<O> argumentResult = this.argument.parse(context,
                argument.copyFor(this.argument));
        try {
            T mappedResult = this.function.applyThrow(argumentResult.value());
            return new CommandArgumentResult<>(argumentResult.position(), mappedResult);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext, @NotNull CommandArgumentContext<T> argument) {
        return this.argument.suggest(commandContext, argument.copyFor(this.argument));
    }
}
