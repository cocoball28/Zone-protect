package org.zone.commands.system;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.context.CommandContext;
import org.zone.commands.system.context.ErrorContext;

import java.util.*;
import java.util.stream.Collectors;

public interface CommandLauncher extends BaseCommandLauncher {

    @NotNull Set<ArgumentCommand> getCommands();

    @Override
    default @NotNull CommandResult run(@NotNull CommandCause source, @NotNull String... args) throws
            NotEnoughArgumentsException {
        CommandContext commandContext = new CommandContext(source, this.getCommands(), args);
        Optional<ArgumentCommand> opCommand = commandContext.getCompleteCommand();
        if (opCommand.isEmpty()) {
            Set<ErrorContext> errors = commandContext.getErrors();
            if (!errors.isEmpty()) {
                ErrorContext error = errors.iterator().next();
                source.sendMessage(Identity.nil(),
                                   Component.text(error.error()).color(NamedTextColor.RED));
                if (errors.size() > 8) {
                    return CommandResult.success();
                }

                errors
                        .parallelStream()
                        .map(e -> e.argument().getUsage())
                        .collect(Collectors.toSet())
                        .forEach(e -> source.sendMessage(Identity.nil(),
                                                         Component
                                                                 .text(e)
                                                                 .color(NamedTextColor.RED)));
            } else {
                source.sendMessage(Identity.nil(),
                                   Component.text("Unknown error").color(NamedTextColor.RED));
            }
            return CommandResult.success();

        }
        if (!opCommand.get().hasPermission(source)) {
            return CommandResult.error(Component
                                               .text("You do not have permission for that command. You " +
                                                             "require " +
                                                             opCommand.get().getPermissionNode())
                                               .color(NamedTextColor.RED));
        }
        return opCommand.get().run(commandContext, args);
    }

    @Override
    default @NotNull List<CommandCompletion> tab(@NotNull CommandCause source,
                                                 @NotNull String... args) {
        CommandContext commandContext = new CommandContext(source, this.getCommands(), args);
        Set<ArgumentCommand> commands = commandContext.getPotentialCommands();
        TreeSet<CommandCompletion> tab = new TreeSet<>(Comparator.comparing(CommandCompletion::completion));
        commands.forEach(c -> {
            if (!c.hasPermission(source)) {
                return;
            }
            tab.addAll(commandContext.getSuggestions(c));
        });
        return new ArrayList<>(tab);
    }

}
