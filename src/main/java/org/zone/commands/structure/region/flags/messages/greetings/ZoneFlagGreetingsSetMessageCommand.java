package org.zone.commands.structure.region.flags.messages.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.sponge.ComponentRemainingArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsSetMessageCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_VALUE =  new ZoneArgument("zoneId",ZonePermissions.OVERRIDE_FLAG_GREETINGS_MESSAGE_SET,            new ZoneArgumentFilterBuilder()
            .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
            .build());
    public static final ComponentRemainingArgument MESSAGE_VALUE = new ComponentRemainingArgument(
            "message_value");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_VALUE,
                new ExactArgument("greetings"),
                new ExactArgument("message"),
                new ExactArgument("set"),
                MESSAGE_VALUE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getGreetingsSetMessageCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_GREETINGS_MESSAGE_SET);
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        Component message = commandContext.getArgument(this, MESSAGE_VALUE);
        GreetingsFlag greetingsflag = zone
                .getFlag(FlagTypes.GREETINGS)
                .orElse(new GreetingsFlag(Messages.getEnterGreetingsMessage(),
                        MessageDisplayTypes.CHAT.createCopyOfDefault()));
        greetingsflag.setMessage(message);
        zone.setFlag(greetingsflag);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getGreetingMessageSet(message));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(e));
        }
        return CommandResult.success();
    }

}