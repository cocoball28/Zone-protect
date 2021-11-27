package org.zone.commands.system;

import org.zone.commands.system.context.CommandArgumentContext;

public record CommandArgumentResult<T>(int position, T value) {

    public static <T> CommandArgumentResult<T> from(CommandArgumentContext<T> argumentContext, T value) {
        return from(argumentContext, 1, value);
    }

    public static <T> CommandArgumentResult<T> from(CommandArgumentContext<T> argumentContext, int length, T value) {
        return new CommandArgumentResult<>(argumentContext.getFirstArgument() + length, value);
    }
}
