package org.zone.commands.system.arguments.simple.number;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class DoubleArgument implements CommandArgument<Double> {

    private final @NotNull String id;

    public DoubleArgument(@NotNull String id) {
        this.id = id;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<Double> parse(
            @NotNull CommandContext context,
            @NotNull CommandArgumentContext<Double> argument) throws IOException {
        try {
            return CommandArgumentResult.from(argument,
                    Double.parseDouble(argument.getFocusArgument()));
        } catch (NumberFormatException e) {
            throw new IOException(e);
        }
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext,
            @NotNull CommandArgumentContext<Double> argument) {
        return Collections.emptyList();
    }
}
