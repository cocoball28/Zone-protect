package org.zone.commands.structure.region.flags.messages.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.utils.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zone_value",
                                                             new ZoneArgument.ZoneArgumentPropertiesBuilder());

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
        return Component.text("View the leaving message flag");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull Optional<LeavingFlag> opFlag = zone.getFlag(FlagTypes.LEAVING);
        if (opFlag.isEmpty()) {
            commandContext.sendMessage(Messages.getNoMessageSet());
            return CommandResult.success();
        }
        commandContext.sendMessage(Messages.getZoneFlagLeavingMessageViewCommandMessageViewMessage(opFlag.get()));
        return CommandResult.success();
    }
}
