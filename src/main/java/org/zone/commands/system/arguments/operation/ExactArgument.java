package org.zone.commands.system.arguments.operation;

import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ExactArgument implements CommandArgument<String> {

    private final String id;
    private final String[] lookup;
    private final boolean caseSens;

    public ExactArgument(String id) {
        this(id, false, id);
    }

    public ExactArgument(String id, boolean caseSens, String... lookup) {
        if (lookup.length==0) {
            throw new IllegalArgumentException("Lookup cannot be []");
        }
        this.id = id;
        this.lookup = lookup;
        this.caseSens = caseSens;
    }

    public String[] getLookup() {
        return this.lookup;
    }

    @Override
    public String getId() {
        return this.id;
    }

    private boolean anyMatch(String arg) {
        for (String a : this.lookup) {
            if ((this.caseSens && a.equals(arg)) || (!this.caseSens && a.equalsIgnoreCase(arg))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CommandArgumentResult<String> parse(CommandContext context, CommandArgumentContext<String> argument) throws IOException {
        String arg = context.getCommand()[argument.getFirstArgument()];
        if (this.anyMatch(arg)) {
            return CommandArgumentResult.from(argument, arg);
        }
        throw new IOException("Unknown argument of '" + arg + "'");
    }

    @Override
    public Set<CommandCompletion> suggest(CommandContext context, CommandArgumentContext<String> argument) {
        String arg = "";
        if (context.getCommand().length > argument.getFirstArgument()) {
            arg = context.getCommand()[argument.getFirstArgument()];
        }
        final String finalArg = arg.toLowerCase();
        return Arrays.stream(this.lookup).parallel().map(String::toLowerCase)
                .filter(look -> look.startsWith(finalArg))
                .map(CommandCompletion::of)
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsage() {
        return "<" +
                Arrays
                        .stream(this.lookup)
                        .map(l -> "\"" + l + "\"")
                        .collect(Collectors.joining(" / "))
                + ">";
    }
}