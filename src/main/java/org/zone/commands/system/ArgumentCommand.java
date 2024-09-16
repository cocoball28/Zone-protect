package org.zone.commands.system;


import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;

import java.util.List;
import java.util.Optional;

/**
 * An argument command is a command that uses {@link CommandArgument} as its arguments.
 *
 * @since 1.0.0
 */
public interface ArgumentCommand {

    /**
     * Gets the arguments of the command.
     * This should be a list of arguments that do not change
     *
     * @return A list of command arguments
     * @since 1.0.0
     */
    @NotNull List<CommandArgument<?>> getArguments();

    /**
     * Gets a description of the command, designing to inform the user on what the command does.
     *
     * @return A string of the description
     * @since 1.0.0
     */
    @NotNull Component getDescription();

    /**
     * Gets the permission node of the command that is required to run the command.
     * If a permission is not required then this should return {@link Optional#empty()}
     * <p>
     * When checking to see if this command has permission you should use {@link #hasPermission(CommandCause)}
     *
     * @return The permission to the command
     * @since 1.0.0
     */
    @NotNull Optional<ZonePermission> getPermissionNode();

    /**
     * Runs the command
     *
     * @param commandContext The command context for this command
     * @param args           The arguments for the command
     *
     * @return If the command should show the usage (false to show)
     * @since 1.0.0
     */
    @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args);

    /**
     * If the command source has permission to run this command
     *
     * @param source The command source to compare
     *
     * @return If the source has permission to run the command
     * @since 1.0.0
     */
    default boolean hasPermission(@NotNull CommandCause source) {
        Optional<ZonePermission> opNode = this.getPermissionNode();
        return opNode.map(permission -> permission.hasPermission(source.subject())).orElse(true);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    default boolean canApply(@NotNull CommandContext context) {
        return this.getArguments().stream().allMatch(arg -> arg.canApply(context));
    }

}