package org.zone.commands.system.arguments.sponge;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.client.ClientLocation;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.math.vector.Vector3d;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

public class LocationArgument implements CommandArgument<Location<?, ?>> {

    private final @NotNull String id;
    private final @NotNull Function<? super String, ? extends Number> toNumber;
    private final Function<Location<?, ?>, Vector3d> suggestion;
    private final Function<Double, String> suggestionFormat;

    public static final Function<Location<?, ?>, Vector3d> AS_EXACT = Location::position;
    public static final Function<Location<?, ?>, Vector3d> AS_BLOCK = loc -> loc
            .blockPosition()
            .toDouble();

    public static final Function<Double, String> DOUBLE_FORMAT = Object::toString;
    public static final Function<Double, String> INTEGER_FORMAT = d -> d.intValue() + "";

    public LocationArgument(
            @NotNull String id,
            @NotNull Function<? super String, ? extends Number> toNumber,
            Function<Location<?, ?>, Vector3d> suggestion,
            Function<Double, String> suggestionFormat) {
        this.id = id;
        this.toNumber = toNumber;
        this.suggestion = suggestion;
        this.suggestionFormat = suggestionFormat;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<Location<?, ?>> parse(
            @NotNull CommandContext context,
            @NotNull CommandArgumentContext<Location<?, ?>> argument) throws IOException {
        int firstArgumentPos = argument.getFirstArgument();
        String[] commandArguments = context.getCommand();
        if (commandArguments.length < firstArgumentPos + 3) {
            throw new IOException("Not enough arguments");
        }

        double x;
        double y;
        double z;

        try {
            x = this.toNumber.apply(commandArguments[firstArgumentPos]).doubleValue();
            y = this.toNumber.apply(commandArguments[firstArgumentPos + 1]).doubleValue();
            z = this.toNumber.apply(commandArguments[firstArgumentPos + 2]).doubleValue();
        } catch (Exception e) {
            throw new IOException(e);
        }
        if (Sponge.isClientAvailable()) {
            ClientLocation loc = Sponge
                    .client()
                    .world()
                    .map(world -> world.location(x, y, z))
                    .orElseThrow(() -> new IOException(""));
            return CommandArgumentResult.from(argument, 3, loc);
        }

        if (commandArguments.length < firstArgumentPos + 4) {
            throw new IOException("Not enough arguments");
        }

        String worldId = commandArguments[firstArgumentPos + 4];
        ResourceKey key = ResourceKey.resolve(worldId);
        Optional<ServerWorld> opWorld = Sponge.server().worldManager().world(key);
        if (opWorld.isEmpty()) {
            throw new IOException("Cannot get world of " + worldId);
        }


        return CommandArgumentResult.from(argument, 4, opWorld.get().location(x, y, z));
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext,
            @NotNull CommandArgumentContext<Location<?, ?>> argument) {
        if (!(commandContext.getSource() instanceof Locatable locatable)) {
            return Collections.emptyList();
        }
        int firstArgumentPos = argument.getFirstArgument();
        int difference = commandContext.getCommand().length - firstArgumentPos;
        return switch (difference) {
            case 0 -> Collections.singleton(CommandCompletion.of(this.suggestionFormat.apply(this.suggestion
                    .apply(locatable.location())
                    .x())));
            case 1 -> Collections.singleton(CommandCompletion.of(this.suggestionFormat.apply(this.suggestion
                    .apply(locatable.location())
                    .y())));
            case 2 -> Collections.singleton(CommandCompletion.of(this.suggestionFormat.apply(this.suggestion
                    .apply(locatable.location())
                    .z())));
            case 3 -> {
                World<?, ?> world = locatable.world();
                if (!(world instanceof ServerWorld sWorld)) {
                    yield Collections.emptySet();
                }
                yield Collections.singleton(CommandCompletion.of(sWorld.key().formatted()));
            }
            default -> Collections.emptyList();
        };
    }
}
