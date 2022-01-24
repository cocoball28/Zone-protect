package org.zone.config.command;

import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.context.CommandContext;

public interface ConfigCommandNode<V> {

    String getDisplayId();

    CommandArgument<V> getCommandArgument();

    CommandResult onChange(CommandContext context, V newValue);

}
