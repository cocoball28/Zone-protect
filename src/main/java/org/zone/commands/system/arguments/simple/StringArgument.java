package org.zone.commands.system.arguments.simple;

import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.util.Collections;
import java.util.Set;

public class StringArgument implements CommandArgument<String> {

    private final String id;

    public StringArgument(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<String> parse(CommandContext context,
                                               CommandArgumentContext<String> argument) {
        String text = context.getCommand()[argument.getFirstArgument()];
        return CommandArgumentResult.from(argument, text);

    }

    @Override
    public Set<CommandCompletion> suggest(CommandContext commandContext,
                                          CommandArgumentContext<String> argument) {
        return Collections.emptySet();
    }
}
