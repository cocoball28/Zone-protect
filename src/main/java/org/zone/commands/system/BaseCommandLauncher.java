package org.zone.commands.system;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;

import java.util.List;

public interface BaseCommandLauncher {

    @NotNull String getName();

    @NotNull String getDescription();

    boolean hasPermission(@NotNull CommandCause context);

    default @NotNull String getUsage(@NotNull CommandCause context) {
        return this.getName();
    }

    @NotNull CommandResult run(@NotNull CommandCause context, @NotNull String... args) throws
            NotEnoughArgumentsException;

    @NotNull List<CommandCompletion> tab(@NotNull CommandCause source, @NotNull String... args);
}
