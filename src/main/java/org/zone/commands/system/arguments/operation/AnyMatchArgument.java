package org.zone.commands.system.arguments.operation;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnyMatchArgument<T> implements CommandArgument<T> {

    private final @NotNull String id;
    private final @NotNull Function<? super T, String> asString;
    private final @NotNull Collection<? extends T> results;

    @Deprecated
    public AnyMatchArgument(String id, Function<? super T, String> asString) {
        throw new RuntimeException("AnyMatchArgument requires at least one object to match");
    }

    @SafeVarargs
    public AnyMatchArgument(@NotNull String id, @NotNull Function<? super T, String> asString,
            T... results) {
        this(id, asString, Arrays.asList(results));
    }

    public AnyMatchArgument(
            @NotNull String id, @NotNull Function<? super T, String> asString,
            @NotNull Collection<? extends T> results) {
        this.id = id;
        this.asString = asString;
        this.results = results;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<T> parse(
            @NotNull CommandContext context, @NotNull CommandArgumentContext<T> argument) throws
            IOException {
        String peek = argument.getFocusArgument();
        return this.results
                .stream()
                .filter(result -> this.asString.apply(result).equalsIgnoreCase(peek))
                .findAny()
                .map(result -> CommandArgumentResult.from(argument, result))
                .orElseThrow(() -> new IOException("Unknown match"));
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext, @NotNull CommandArgumentContext<T> argument) {
        String peek = argument.getFocusArgument().toLowerCase();
        return this.results
                .stream()
                .filter(result -> this.asString.apply(result).toLowerCase().startsWith(peek))
                .map(result -> CommandCompletion.of(this.asString.apply(result)))
                .collect(Collectors.toSet());
    }
}
