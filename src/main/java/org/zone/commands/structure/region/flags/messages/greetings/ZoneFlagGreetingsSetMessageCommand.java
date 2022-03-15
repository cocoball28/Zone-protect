package org.zone.commands.structure.region.flags.messages.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.AnyMatchArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.sponge.ComponentRemainingArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayType;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayTypes;
import org.zone.region.flag.entity.player.move.message.greetings.GreetingsFlag;
import org.zone.utils.Messages;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsSetMessageCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_FLAG_GREETINGS_SET));
    public static final ComponentRemainingArgument MESSAGE_VALUE = new ComponentRemainingArgument(
            "message_value");
    public static final OptionalArgument<MessageDisplayType<?>> DISPLAY_MODE =
            new OptionalArgument<>(new AnyMatchArgument<>("displayMode",
                    MessageDisplayType::getId
                    , ZonePlugin
                    .getZonesPlugin()
                    .getMessageDisplayManager()
                    .getDisplayTypes()),
                    MessageDisplayTypes.CHAT_MESSAGE_DISPLAY);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_VALUE,
                             new ExactArgument("greetings"),
                             new ExactArgument("message"),
                             new ExactArgument("set"),
                             MESSAGE_VALUE,
                             DISPLAY_MODE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getGreetingsSetMessageCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_GREETINGS_SET);
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        Component message = commandContext.getArgument(this, MESSAGE_VALUE);
        MessageDisplayType<?> displayMode = commandContext.getArgument(this, DISPLAY_MODE);
        if (message == Messages.getEnterGreetingsMessage()) {
            return CommandResult.error(Messages.getSameAsDefaultMessage());
        }
        GreetingsFlag greetingsflag = zone
                .getFlag(FlagTypes.GREETINGS)
                .orElse(new GreetingsFlag(Messages.getEnterGreetingsMessage(), displayMode.createCopyOfDefault()));
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