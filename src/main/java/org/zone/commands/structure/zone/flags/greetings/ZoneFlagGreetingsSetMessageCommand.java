package org.zone.commands.structure.zone.flags.greetings;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.simple.StringArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.greetings.GreetingsFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsSetMessageCommand implements ArgumentCommand {
    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAGS = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value", new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ExactArgument GREETINGS = new ExactArgument("greetings");
    public static final ExactArgument MESSAGE = new ExactArgument("message");
    public static final ExactArgument SET = new ExactArgument("set");
    public static final RemainingArgument<String> MESSAGE_VALUE = new RemainingArgument(new StringArgument("message_value"));

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAGS, ZONE_VALUE, GREETINGS, MESSAGE, SET, MESSAGE_VALUE);
    }

    @Override
    public Component getDescription() {
        return Component.text("Command for setting greeting message for a zone");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String[] args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        List<String> message_value = commandContext.getArgument(this, MESSAGE_VALUE);
        String message = String.join(" ", message_value);
        GreetingsFlag greetingsflag = zone.getFlag(FlagTypes.GREETINGS_FLAG_TYPE).orElse(new GreetingsFlag());
        greetingsflag.setMessage(Component.text(message));
        zone.removeFlag(FlagTypes.GREETINGS_FLAG_TYPE);
        zone.addFlag(greetingsflag);
        try {
            zone.save();
            commandContext.sendMessage(Component.text("Zone greetings message set to: " + message));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Failed to save:" + e.getMessage()));
        }
        return CommandResult.success();
    }

}