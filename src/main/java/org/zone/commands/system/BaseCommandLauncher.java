package org.zone.commands.system;

import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;

import java.util.List;

public interface BaseCommandLauncher {

    String getName();

    String getDescription();

    boolean hasPermission(CommandCause context);

    default String getUsage(CommandCause context) {
        return this.getName();
    }

    CommandResult run(CommandCause context, String... args) throws NotEnoughArgumentsException;

    List<CommandCompletion> tab(CommandCause source, String... args);
}
