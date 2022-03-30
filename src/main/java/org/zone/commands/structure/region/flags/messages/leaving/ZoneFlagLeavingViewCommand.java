package org.zone.commands.structure.region.flags.messages.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
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
import org.zone.region.flag.entity.player.move.leaving.LeavingFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_LEAVING_MESSAGE_VIEW,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.MEMBERS_ONLY)
                    .setPermission(ZonePermissions.FLAG_LEAVING_MESSAGE_VIEW)
                    .build());

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE,
                new ExactArgument("leaving"),
                new ExactArgument("view"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getLeavingViewCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.OVERRIDE_FLAG_LEAVING_MESSAGE_VIEW);
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull Optional<LeavingFlag> opFlag = zone.getFlag(FlagTypes.LEAVING);
        if (opFlag.isEmpty()) {
            commandContext.sendMessage(Messages.getNoMessageSet());
            return CommandResult.success();
        }
        commandContext.sendMessage(Messages.getLeavingMessage(opFlag.get()));
        commandContext.sendMessage(Messages.getFlagMessageDisplayTypeView(opFlag.get().getDisplayType().getType()));
        return CommandResult.success();
    }
}

