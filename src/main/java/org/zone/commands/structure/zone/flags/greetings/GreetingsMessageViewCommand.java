package org.zone.commands.structure.zone.flags.greetings;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.greetings.GreetingsFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GreetingsMessageViewCommand implements ArgumentCommand {
    public static final ExactArgument ZONE = new ExactArgument("zone");
    public static final ExactArgument FLAGS = new ExactArgument("flags");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value", new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ExactArgument GREETINGS = new ExactArgument("greetings");
    public static final ExactArgument MESSAGE = new ExactArgument("message");
    public static final ExactArgument VIEW = new ExactArgument("view");

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(ZONE, FLAGS, ZONE_VALUE, GREETINGS, VIEW);
    }

    @Override
    public Component getDescription() {
        return Component.text("Command for viewing the greeting message of a specific zone");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String[] args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        GreetingsFlag greetingsFlag = zone.getFlag(FlagTypes.GREETINGS_FLAG_TYPE).orElse(new GreetingsFlag());
        Optional<Component> opMessage = greetingsFlag.getMessage();
        if (opMessage.isEmpty()) {
            Component.text("Message: ").append(Component.text("No text"));
        }
        else {
            Component.text("Message: ").append(opMessage.get());
        }
        return null;
    }

}