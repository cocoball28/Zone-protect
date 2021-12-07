package org.zone.commands.system.arguments.message;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Collection;

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
        return null;
    }

    @Override
    public Collection<CommandCompletion> suggest(CommandContext commandContext, CommandArgumentContext<Component> argument) {
        return null;
    }
}
