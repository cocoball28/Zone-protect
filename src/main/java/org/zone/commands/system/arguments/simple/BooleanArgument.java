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
    private final String asTrue;
    private final String asFalse;

    public BooleanArgument(String id) {
        this(id, "true", "false");
    }

    public BooleanArgument(String id, String trueString, String falseString) {
        this.id = id;
        this.asFalse = falseString;
        this.asTrue = trueString;
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
        if (arg.equals(this.asTrue)) {
            return CommandArgumentResult.from(argument, true);
        }
        if (arg.equals(this.asFalse)) {
            return CommandArgumentResult.from(argument, false);
        }
        throw new IOException("'" +
                                      arg +
                                      "' is not either '" +
                                      this.asTrue +
                                      "' or '" +
                                      this.asFalse +
                                      "'");
    }

    @Override
    public Set<CommandCompletion> suggest(CommandContext commandContext,
                                          CommandArgumentContext<Boolean> argument) {
        String peek = commandContext.getCommand()[argument.getFirstArgument()];
        Set<CommandCompletion> list = new HashSet<>();
        if (this.asTrue.startsWith(peek.toLowerCase())) {
            list.add(CommandCompletion.of(this.asTrue));
        }
        if (this.asFalse.startsWith(peek.toLowerCase())) {
            list.add(CommandCompletion.of(this.asFalse));
        }
        return list;
    }
}