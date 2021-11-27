package org.zone.commands.system.arguments.operation;

import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.ParseCommandArgument;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Collection;

public class OptionalArgument<T> implements CommandArgument<T> {

    public static class WrappedParser<T> implements ParseCommandArgument<T> {

        private final T value;

        public WrappedParser(T value) {
            this.value = value;
        }

        @Override
        public CommandArgumentResult<T> parse(CommandContext context, CommandArgumentContext<T> argument) {
            return CommandArgumentResult.from(argument, 0, this.value);
        }
    }

    private final CommandArgument<T> arg;
    private final ParseCommandArgument<T> value;

    public OptionalArgument(CommandArgument<T> arg, T value) {
        this(arg, new WrappedParser<>(value));
    }

    public OptionalArgument(CommandArgument<T> arg, ParseCommandArgument<T> value) {
        this.arg = arg;
        this.value = value;
    }

    public CommandArgument<T> getOriginalArgument() {
        return this.arg;
    }

    @Override
    public String getId() {
        return this.arg.getId();
    }

    @Override
    public CommandArgumentResult<T> parse(CommandContext context, CommandArgumentContext<T> argument) throws IOException {
        if (context.getCommand().length==argument.getFirstArgument()) {
            return CommandArgumentResult.from(argument, 0, this.value.parse(context, argument).value());
        }
        try {
            return this.arg.parse(context, argument);
        } catch (IOException e) {
            return CommandArgumentResult.from(argument, 0, this.value.parse(context, argument).value());
        }
    }

    @Override
    public Collection<CommandCompletion> suggest(CommandContext commandContext, CommandArgumentContext<T> argument) {
        return this.arg.suggest(commandContext, argument);
    }

    @Override
    public String getUsage() {
        String original = this.getOriginalArgument().getUsage();
        return "[" + original.substring(1, original.length() - 1) + "]";
    }
}