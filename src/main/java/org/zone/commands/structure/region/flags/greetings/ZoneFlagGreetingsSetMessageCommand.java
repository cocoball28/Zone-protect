package org.zone.commands.structure.region.flags.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.sponge.ComponentRemainingArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.move.player.greetings.GreetingsFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsSetMessageCommand implements ArgumentCommand {
    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAGS = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value",
                                                                   new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ExactArgument GREETINGS = new ExactArgument("greetings");
    public static final ExactArgument MESSAGE = new ExactArgument("message");
    public static final ExactArgument SET = new ExactArgument("set");
    public static final ComponentRemainingArgument MESSAGE_VALUE = new ComponentRemainingArgument(
            "message_value");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAGS, ZONE_VALUE, GREETINGS, MESSAGE, SET, MESSAGE_VALUE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Command for setting greeting message for a zone");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        Component message = commandContext.getArgument(this, MESSAGE_VALUE);
        GreetingsFlag greetingsflag = zone.getFlag(FlagTypes.GREETINGS).orElse(new GreetingsFlag());
        greetingsflag.setMessage(message);
        zone.setFlag(greetingsflag);
        try {
            zone.save();
            commandContext.sendMessage(Component
                                               .text("Zone greetings message set to: ")
                                               .append(message));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Failed to save:" + e.getMessage()));
        }
        return CommandResult.success();
    }

}