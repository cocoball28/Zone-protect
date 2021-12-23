package org.zone.commands.structure.region.flags.greetings;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsRemoveCommand implements ArgumentCommand {
    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAGS = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value",
                                                                   new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ExactArgument GREETINGS = new ExactArgument("greetings");
    public static final ExactArgument REMOVE = new ExactArgument("remove");

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAGS, ZONE_VALUE, GREETINGS, REMOVE);
    }

    @Override
    public Component getDescription() {
        return Component.text("Command for removing the greetings message");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws
            NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        zone.removeFlag(FlagTypes.GREETINGS);
        try {
            zone.save();
            commandContext.sendMessage(Component.text("Zone greetings message removed from this " +
                                                              "zone!"));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Failed to save:" + e.getMessage()));
        }
        return CommandResult.success();
    }
}