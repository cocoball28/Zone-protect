package org.zone.commands.structure.region.flags.messages.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsRemoveCommand implements ArgumentCommand {
    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAGS = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value");
    public static final ExactArgument GREETINGS = new ExactArgument("greetings");
    public static final ExactArgument REMOVE = new ExactArgument("remove");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAGS, ZONE_VALUE, GREETINGS, REMOVE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Command for removing the greetings message");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        zone.removeFlag(FlagTypes.GREETINGS);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getZoneFlagGreetingsRemoveCommandGreetingsRemovedMessage());
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(e));
        }
        return CommandResult.success();
    }
}