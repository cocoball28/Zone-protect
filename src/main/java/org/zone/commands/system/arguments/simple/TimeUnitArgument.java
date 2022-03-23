package org.zone.commands.system.arguments.simple;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;
import org.zone.utils.time.TimeUnits;

import java.io.IOException;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TimeUnitArgument implements CommandArgument<TemporalUnit> {

    private final @NotNull String id;
    private final @NotNull Map<String, TemporalUnit> units = new HashMap<>();


    public TimeUnitArgument(@NotNull String id, @NotNull TimeUnits... units) {
        this(id, Arrays.asList(units));
    }

    public TimeUnitArgument(@NotNull String id, @NotNull Collection<TimeUnits> units) {
        this(id, units.stream().collect(Collectors.toMap(Enum::name, TimeUnits::getUnit)));
    }

    public TimeUnitArgument(@NotNull String id) {
        this(id,
                Arrays
                        .stream(TimeUnits.values())
                        .collect(Collectors.toMap(Enum::name, TimeUnits::getUnit)));
    }

    public TimeUnitArgument(@NotNull String id, @NotNull Map<String, TemporalUnit> units) {
        this.id = id;
        this.units.putAll(units);
    }

    public @NotNull Map<String, TemporalUnit> getUnits() {
        return this.units;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<TemporalUnit> parse(
            @NotNull CommandContext context,
            @NotNull CommandArgumentContext<TemporalUnit> argument) throws IOException {
        String arg = argument.getFocusArgument();
        return this.units
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(arg))
                .findAny()
                .map(entry -> CommandArgumentResult.from(argument, entry.getValue()))
                .orElseThrow(() -> new IOException("Unknown time unit"));
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext,
            @NotNull CommandArgumentContext<TemporalUnit> argument) {
        String peek = argument.getFocusArgument();
        return this.units
                .keySet()
                .stream()
                .filter(key -> key.toLowerCase().startsWith(peek.toLowerCase()))
                .map(CommandCompletion::of)
                .collect(Collectors.toSet());
    }
}
