package org.zone.commands.structure;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.ArgumentReader;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.context.CommandContext;
import org.zone.commands.system.context.ErrorContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The wrapper that converts a Sponge {@link Command.Raw} into the executor for the ZoneCommand.
 */
public class ZoneSpongeCommand implements Command.Raw {

    public final Set<ArgumentCommand> commands = new HashSet<>();

    /**
     * Do not use
     *
     * @throws RuntimeException requires at least 1 CommandArgument
     */
    @Deprecated
    public ZoneSpongeCommand() {
        throw new RuntimeException("A ArgumentCommand needs to be specified");
    }

    /**
     * Constructor
     *
     * @param commands The possible commands for this command
     */
    public ZoneSpongeCommand(ArgumentCommand... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Constructor
     *
     * @param commands The possible commands for this command
     */
    public ZoneSpongeCommand(Collection<? extends ArgumentCommand> commands) {
        this.commands.addAll(commands);
    }

    /**
     * Adds the command into the /zone command
     * @param command The command to add
     * @return if the command was added
     */
    public boolean add(ArgumentCommand command){
        return this.commands.add(command);
    }

    /**
     * Removes the command from /zone
     * @param command The command to remove
     * @return if the command was removed
     */
    public boolean remove(ArgumentCommand command){
        return this.commands.remove(command);
    }

    @Override
    public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
        String input = arguments.input();
        String[] args = input.split(" ");
        if (input.endsWith(" ")) {
            String[] newArgs = new String[args.length + 1];
            System.arraycopy(args, 0, newArgs, 0, args.length);
            newArgs[args.length] = "";
            args = newArgs;
        }
        CommandContext commandContext = new CommandContext(cause, this.commands, args);
        Optional<ArgumentCommand> opCommand = commandContext.getCompleteCommand();
        if (opCommand.isEmpty()) {
            Set<ErrorContext> errors = commandContext.getErrors();
            if (!errors.isEmpty()) {
                ErrorContext error = errors.iterator().next();
                cause.sendMessage(Identity.nil(), Component.text(error.error()).color(NamedTextColor.RED));
                errors
                        .parallelStream()
                        .map(e -> e.argument().getUsage())
                        .collect(Collectors.toSet())
                        .forEach(e -> cause.sendMessage(Identity.nil(), Component.text(e).color(NamedTextColor.RED)));
            } else {
                cause.sendMessage(Identity.nil(), Component.text("Unknown error").color(NamedTextColor.RED));
            }
            return CommandResult.success();
        }
        if (!opCommand.get().hasPermission(cause)) {
            cause.sendMessage(Identity.nil(), Component
                    .text(" You do not have permission for that command" +
                            ". You" +
                            " require " +
                            opCommand.get().getPermissionNode())
                    .color(NamedTextColor.RED));
            return CommandResult.success();
        }
        try {
            return opCommand.get().run(commandContext, arguments.remaining().split(" "));
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            if (message == null) {
                message = "Unknown error";
            }
            throw new CommandException(Component.text(message), e);
        }
    }

    @Override
    public List<CommandCompletion> complete(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
        String input = arguments.input();
        String[] args = input.split(" ");
        if (input.endsWith(" ")) {
            String[] newArgs = new String[args.length + 1];
            System.arraycopy(args, 0, newArgs, 0, args.length);
            newArgs[args.length] = "";
            args = newArgs;
        }
        CommandContext commandContext = new CommandContext(cause, this.commands, args);
        Set<ArgumentCommand> commands = commandContext.getPotentialCommands();
        TreeSet<CommandCompletion> tab = new TreeSet<>(Comparator.comparing(CommandCompletion::completion));
        commands.forEach(c -> {
            if (!c.hasPermission(cause)) {
                return;
            }
            if(!c.canApply(commandContext)){
                return;
            }
            tab.addAll(commandContext.getSuggestions(c));
        });
        return new ArrayList<>(tab);
    }

    @Override
    public boolean canExecute(CommandCause cause) {
        return this.commands.stream().anyMatch(command -> command.hasPermission(cause));
    }

    @Override
    public Optional<Component> shortDescription(CommandCause cause) {
        return Optional.of(Component.text("All Zone commands"));
    }

    @Override
    public Optional<Component> extendedDescription(CommandCause cause) {
        return Optional.of(Component.text("All commands for the plugin Zones"));
    }

    @Override
    public Component usage(CommandCause cause) {
        return Component.text("/Zone <Arg>");
    }
}
