package org.zone.commands.system.arguments.sponge;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;
import org.zone.utils.component.ZoneComponentParser;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class ComponentRemainingArgument implements CommandArgument<Component> {

    private final String id;

    public ComponentRemainingArgument(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<Component> parse(CommandContext context, CommandArgumentContext<Component> argument) throws IOException {
        String value = argument.getFocusArgument();
        try {
            return CommandArgumentResult.from(argument, argument.getRemainingArguments().length, ZoneComponentParser.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Collection<CommandCompletion> suggest(CommandContext commandContext, CommandArgumentContext<Component> argument) {
        String peek = argument.getFocusArgument();
        return ZoneComponentParser.getSuggestion(peek).stream().map(CommandCompletion::of).collect(Collectors.toSet());
    }
}
