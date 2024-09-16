package org.zone.commands.system.arguments.simple.number;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.ParseCommandArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Get a number between a range and fails if the input is above or below that range, such as
 * 0-10, an input of -1 would fail the argument while a number of 0 would be accepted
 *
 * @param <N> The number type that should be accepted
 * @since 1.0.0
 */
public class RangeArgument<N extends Number> implements CommandArgument<N> {

    private final CommandArgument<N> parser;
    private final ParseCommandArgument<N> lower;
    private final ParseCommandArgument<N> higher;

    /**
     * Creates a range argument
     *
     * @param parser the command argument to gain the number
     * @param lower  the minimum value that can be accepted
     * @param higher the maximum value that can be accepted
     * @since 1.0.0
     */
    public RangeArgument(
            CommandArgument<N> parser,
            ParseCommandArgument<N> lower,
            ParseCommandArgument<N> higher) {
        this.parser = parser;
        this.lower = lower;
        this.higher = higher;
    }

    @Override
    public @NotNull String getId() {
        return this.parser.getId();
    }

    @Override
    public CommandArgumentResult<N> parse(
            @NotNull CommandContext context, @NotNull CommandArgumentContext<N> argument) throws
            IOException {
        CommandArgumentResult<N> numberResult = this.parser.parse(context, argument);
        N number = numberResult.value();
        N lower = this.lower.parse(context, argument).value();
        N higher = this.higher.parse(context, argument).value();
        if (number.doubleValue() < lower.doubleValue() ||
                number.doubleValue() > higher.doubleValue()) {
            throw new IOException("Number of " +
                    number +
                    " wasn't between " +
                    lower +
                    "-" +
                    higher);
        }
        return numberResult;
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext, @NotNull CommandArgumentContext<N> argument) {
        try {
            N lowest = this.lower.parse(commandContext, argument).value();
            N highest = this.higher.parse(commandContext, argument).value();

            Collection<CommandCompletion> ret = new HashSet<>();
            ret.add(CommandCompletion.of(lowest.toString()));
            ret.add(CommandCompletion.of(highest.toString()));
            for (int value = lowest.intValue() + 1; value < highest.intValue(); value++) {
                ret.add(CommandCompletion.of(value + ""));
            }
            return ret;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Creates a range argument based on a fixed input
     *
     * @param id  The id of the argument
     * @param min the minimum value that can be accepted
     * @param max the maximum value that can be accepted
     *
     * @return The created range argument
     * @since 1.0.0
     */
    public static RangeArgument<Double> createArgument(String id, double min, double max) {
        return new RangeArgument<>(new DoubleArgument(id),
                new OptionalArgument.WrappedParser<>(min),
                new OptionalArgument.WrappedParser<>(max));
    }


    /**
     * Creates a range argument based on a fixed input
     *
     * @param id  The id of the argument
     * @param min the minimum value that can be accepted
     * @param max the maximum value that can be accepted
     *
     * @return The created range argument
     * @since 1.0.0
     */
    public static RangeArgument<Integer> createArgument(String id, int min, int max) {
        return new RangeArgument<>(new IntegerArgument(id),
                new OptionalArgument.WrappedParser<>(min),
                new OptionalArgument.WrappedParser<>(max));
    }
}
