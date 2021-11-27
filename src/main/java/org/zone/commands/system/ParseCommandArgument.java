package org.zone.commands.system;

import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;

public interface ParseCommandArgument<T> {

    CommandArgumentResult<T> parse(CommandContext context, CommandArgumentContext<T> argument) throws IOException;

}
