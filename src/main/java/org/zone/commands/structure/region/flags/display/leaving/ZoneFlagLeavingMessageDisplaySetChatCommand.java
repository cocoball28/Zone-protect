package org.zone.commands.structure.region.flags.display.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingMessageDisplaySetChatCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_LEAVING_MESSAGE_SET_CHAT,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .setPermission(ZonePermissions.FLAG_LEAVING_MESSAGE_SET_CHAT)
                    .build());

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_ID,
                new ExactArgument("leaving"),
                new ExactArgument("message"),
                new ExactArgument("display"),
                new ExactArgument("set"),
                new ExactArgument("chat"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getLeavingDisplaySetChatCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_LEAVING_MESSAGE_SET_CHAT);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        Optional<LeavingFlag> opLeavingFlag = zone.getFlag(FlagTypes.LEAVING);
        if (opLeavingFlag.isEmpty()) {
            return CommandResult.error(Messages.getLeavingFlagNotFound());
        }
        opLeavingFlag.get().setDisplayType(MessageDisplayTypes.CHAT.createCopyOfDefault());
        try {
            zone.save();
            commandContext.sendMessage(Messages.getFlagMessageDisplaySuccessfullyChangedTo(opLeavingFlag.get().getType(), MessageDisplayTypes.CHAT));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
