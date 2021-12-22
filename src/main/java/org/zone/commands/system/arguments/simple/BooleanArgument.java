package org.zone.commands.system.arguments.simple;

import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BooleanArgument implements CommandArgument<Boolean> {

    private final String id;

    public BooleanArgument(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<Boolean> parse(CommandContext context,
                                                CommandArgumentContext<Boolean> argument) throws
            IOException {
        String arg = context.getCommand()[argument.getFirstArgument()];
        if (arg.equals("true")) {
            return CommandArgumentResult.from(argument, true);
        }
        if (arg.equals("false")) {
            return CommandArgumentResult.from(argument, false);
        }
        throw new IOException("'" + arg + "' is not either 'true' or 'false'");
    }

    @Override
    public Set<CommandCompletion> suggest(CommandContext commandContext,
                                          CommandArgumentContext<Boolean> argument) {
        String peek = commandContext.getCommand()[argument.getFirstArgument()];
        Set<CommandCompletion> list = new HashSet<>();
        if ("true".startsWith(peek.toLowerCase())) {
            list.add(CommandCompletion.of("true"));
        }
        if ("false".startsWith(peek.toLowerCase())) {
            list.add(CommandCompletion.of("false"));
        }
        return list;
    }
}