package org.zone.commands.structure.region.flags.leaving;

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
import org.zone.region.flag.move.player.leaving.LeavingFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingSetMessageCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zone_value",
                                                             new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ComponentRemainingArgument MESSAGE = new ComponentRemainingArgument(
            "message_value");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("leaving"),
                             new ExactArgument("set"),
                             new ExactArgument("message"),
                             MESSAGE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Sets the message of the leaving message");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        Component message = commandContext.getArgument(this, MESSAGE);

        LeavingFlag flag = zone
                .getFlag(FlagTypes.LEAVING)
                .orElse(new LeavingFlag(Component.text("Enter leaving message")));

        flag.setLeavingMessage(message);
        zone.setFlag(flag);
        try {
            zone.save();
            commandContext.sendMessage(Component.text("Leaving message is now ").append(message));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Could not save: " + e.getMessage()));
        }
        return CommandResult.success();
    }
}
