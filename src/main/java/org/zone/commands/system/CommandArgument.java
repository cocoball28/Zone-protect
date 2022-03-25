package org.zone.commands.system;

import org.jetbrains.annotations.NotNull;
import org.zone.commands.system.context.CommandContext;

public interface CommandArgument<T> extends ParseCommandArgument<T>, SuggestCommandArgument<T> {

    /**
     * Gets the ID of the command argument.
     * A command argument id needs to be unique within the command, the ID is what separates the arguments.
     * There isn't a standard message structure, however by default the id will be what shows in the usage
     *
     * @return A string ID
     * @since 1.0.0
     */
    @NotNull String getId();

    /**
     * Gets the usage of the argument
     *
     * @return a string version of the usage of the argument
     * @since 1.0.0
     */
    default @NotNull String getUsage() {
        return "<" + this.getId() + ">";
    }

    default boolean canApply(@NotNull CommandContext context) {
        return true;
    }


}
