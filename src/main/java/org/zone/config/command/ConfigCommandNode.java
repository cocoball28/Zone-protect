package org.zone.config.command;

import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.context.CommandContext;

/**
 * A node within the config
 *
 * @param <V> The value that the node should accept
 * @since 1.0.1
 */
public interface ConfigCommandNode<V> {

    String getDisplayId();

    CommandArgument<V> getCommandArgument();

    CommandResult onChange(CommandContext context, V newValue);

}
