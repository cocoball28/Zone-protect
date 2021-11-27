package org.zone.commands.system.context;

import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;

public class CommandArgumentContext<T> {

    private final CommandArgument<T> argument;
    private int firstArgument;
    private String[] command;
    private final ArgumentCommand commandRunner;

    public CommandArgumentContext(ArgumentCommand runner, CommandArgument<T> argument, int firstArgument,
                                  String... command) {
        this.argument = argument;
        this.firstArgument = firstArgument;
        this.command = command;
        this.commandRunner = runner;
    }

    public ArgumentCommand getArgumentCommand() {
        return this.commandRunner;
    }

    public CommandArgument<T> getArgument() {
        return this.argument;
    }

    public String[] getRemainingArguments() {
        int last = this.command.length;

        if (this.firstArgument < last) {
            throw new IndexOutOfBoundsException("min (" + this.firstArgument + ") is greater then max (" + last + ")");
        }
        String[] arr = new String[this.firstArgument - last];
        if (this.firstArgument + 1 - last >= 0) {
            if (last - this.firstArgument >= 0) System.arraycopy(this.command, this.firstArgument, arr, 0,
                    0);
        }
        return arr;
    }

    public String getFocusArgument() {
        return this.command[this.firstArgument];
    }

    public int getFirstArgument() {
        return this.firstArgument;
    }

    public void setCommand(String... args) {
        this.command = args;
    }

    public void setStartArgument(int start) {
        this.firstArgument = start;
    }

}
