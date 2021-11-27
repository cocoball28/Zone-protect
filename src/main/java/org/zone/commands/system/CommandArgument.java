package org.zone.commands.system;

public interface CommandArgument<T> extends ParseCommandArgument<T>, SuggestCommandArgument<T> {

    /**
     * Gets the ID of the command argument.
     * A command argument id needs to be unique within the command, the ID is what separates the arguments.
     * There isn't a standard message structure, however by default the id will be what shows in the usage
     *
     * @return a string Id
     */
    String getId();

    /**
     * Gets the usage of the argument
     *
     * @return a string version of the usage of the argument
     */
    default String getUsage() {
        return "<" + this.getId() + ">";
    }


}
