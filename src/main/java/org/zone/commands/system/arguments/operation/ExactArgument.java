package org.zone.commands.system.arguments.operation;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.GUICommandArgument;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExactArgument implements GUICommandArgument<String> {

    private final @NotNull String id;
    private final @NotNull String[] lookup;
    private final boolean caseSens;

    public ExactArgument(@NotNull String id) {
        this(id, false, id);
    }

    public ExactArgument(@NotNull String id, boolean caseSens, @NotNull String... lookup) {
        if (lookup.length == 0) {
            throw new IllegalArgumentException("Lookup cannot be []");
        }
        this.id = id;
        this.lookup = lookup;
        this.caseSens = caseSens;
    }

    public @NotNull String[] getLookup() {
        return this.lookup;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull String getUsage() {
        return "<" +
                Arrays
                        .stream(this.lookup)
                        .map(lookup -> "\"" + lookup + "\"")
                        .collect(Collectors.joining(" / ")) +
                ">";
    }

    private boolean isAnyMatching(String arg) {
        for (String a : this.lookup) {
            if ((this.caseSens && a.equals(arg)) || (!this.caseSens && a.equalsIgnoreCase(arg))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull CommandArgumentResult<String> parse(
            @NotNull CommandContext context,
            @NotNull CommandArgumentContext<String> argument) throws IOException {
        String arg = context.getCommand()[argument.getFirstArgument()];
        if (this.isAnyMatching(arg)) {
            return CommandArgumentResult.from(argument, arg);
        }
        throw new IOException("Unknown argument of '" + arg + "'");
    }

    @Override
    public @NotNull Set<CommandCompletion> suggest(
            @NotNull CommandContext commandContext,
            @NotNull CommandArgumentContext<String> argument) {
        String arg = "";
        if (commandContext.getCommand().length > argument.getFirstArgument()) {
            arg = commandContext.getCommand()[argument.getFirstArgument()];
        }
        final String finalArg = arg.toLowerCase();
        return Arrays
                .stream(this.lookup)
                .parallel()
                .map(String::toLowerCase)
                .filter(look -> look.startsWith(finalArg))
                .map(CommandCompletion::of)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<ItemStack, String> createMenuOptions(CommandContext context) {
        Map<ItemStack, String> map = new HashMap<>();
        map.put(ItemStack.of(ItemTypes.ITEM_FRAME), this.lookup[0]);
        return map;
    }
}