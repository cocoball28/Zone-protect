package org.zone.commands.structure.region.flags.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.move.player.leaving.LeavingFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zone_value",
                                                             new ZoneArgument.ZoneArgumentPropertiesBuilder());

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("leaving"),
                             new ExactArgument("view"));
    }

    @Override
    public Component getDescription() {
        return Component.text("View the leaving message flag");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws
            NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull Optional<LeavingFlag> opFlag = zone.getFlag(FlagTypes.LEAVING);
        if (opFlag.isEmpty()) {
            commandContext.sendMessage(Component.text("Message: No Message set"));
            return CommandResult.success();
        }
        commandContext.sendMessage(Component
                                           .text("Message: ")
                                           .append(opFlag.get().getLeavingMessage()));
        return CommandResult.success();
    }
}
