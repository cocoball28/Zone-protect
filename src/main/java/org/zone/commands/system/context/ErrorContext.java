package org.zone.commands.system.context;

import org.jetbrains.annotations.NotNull;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;

public record ErrorContext(@NotNull ArgumentCommand command, int argumentFailedAt,
                           @NotNull CommandArgument<?> argument, @NotNull String error) {

}
