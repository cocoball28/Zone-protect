package org.zone.commands.system.arguments.simple;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.GUICommandArgument;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A enum as a command argument -> any enum value is accepted
 * @param <T>
 */
public class EnumArgument<T extends Enum<T>> implements GUICommandArgument<T> {

    private final @NotNull String id;
    private final @NotNull EnumSet<T> set;

    /**
     * Creates the enum argument
     * @param id The id of the argument
     * @param set A set of the enum values -> this can be a subset
     */
    public EnumArgument(@NotNull String id, @NotNull EnumSet<T> set) {
        this.id = id;
        this.set = set;
    }

    /**
     * Creates the enum argument
     * @param id The id of the argument
     * @param enumClass The class of the enum
     */
    public EnumArgument(@NotNull String id, @NotNull Class<T> enumClass) {
        this(id, EnumSet.allOf(enumClass));
    }

    public ItemStack getItem(T entry) {
        return ItemStack
                .builder()
                .quantity(1)
                .itemType(ItemTypes.GRASS_BLOCK)
                .add(Keys.DISPLAY_NAME, Component.text(entry.name().toLowerCase()))
                .build();
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<T> parse(
            @NotNull CommandContext context, @NotNull CommandArgumentContext<T> argument) throws
            IOException {
        String arg = argument.getFocusArgument();
        Optional<T> opValue = this.set
                .parallelStream()
                .filter(t -> t.name().equalsIgnoreCase(arg))
                .findAny();
        if (opValue.isEmpty()) {
            throw new IOException("Unknown value of " + arg);
        }
        return CommandArgumentResult.from(argument, opValue.get());
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext, @NotNull CommandArgumentContext<T> argument) {
        String arg = argument.getFocusArgument();
        return this.set
                .parallelStream()
                .map(value -> CommandCompletion.of(value.name().toLowerCase()))
                .filter(value -> value.completion().toLowerCase().startsWith(arg.toLowerCase()))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(
                        CommandCompletion::completion))));
    }

    @Override
    public Map<ItemStack, String> createMenuOptions(CommandContext context) {
        return this.set.parallelStream().collect(Collectors.toMap(this::getItem, Enum::name));
    }
}
