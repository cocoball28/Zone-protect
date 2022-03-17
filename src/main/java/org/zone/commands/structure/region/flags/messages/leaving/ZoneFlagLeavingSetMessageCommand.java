package org.zone.commands.structure.region.flags.messages.leaving;

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
import org.zone.region.flag.entity.player.display.MessageDisplayType;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingSetMessageCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_FLAG_LEAVING_MESSAGE_SET));
    public static final ComponentRemainingArgument MESSAGE = new ComponentRemainingArgument(
            "message_value");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("leaving"),
                             new ExactArgument("message"),
                             new ExactArgument("set"),
                             MESSAGE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getLeavingSetMessageCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_LEAVING_MESSAGE_SET);
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        Component message = commandContext.getArgument(this, MESSAGE);

        LeavingFlag flag = zone
                .getFlag(FlagTypes.LEAVING)
                .orElse(new LeavingFlag(Messages.getEnterLeavingMessage(),
                        MessageDisplayTypes.CHAT.createCopyOfDefault()));

        flag.setLeavingMessage(message);
        zone.setFlag(flag);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getZoneFlagLeavingMessageSet(message));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(e));
        }
        return CommandResult.success();
    }
}
