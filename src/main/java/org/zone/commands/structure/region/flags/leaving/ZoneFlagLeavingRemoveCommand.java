package org.zone.commands.structure.region.flags.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingRemoveCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zone_value",
                                                             new ZoneArgument.ZoneArgumentPropertiesBuilder());

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("leaving"),
                             new ExactArgument("remove"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Removes the leaving flag");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        zone.removeFlag(FlagTypes.LEAVING);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getZoneFlagLeavingRemoveCommandRemovedMessage());
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.setUniversalZoneSavingErrorMessage(e));
        }
        return CommandResult.success();
    }
}
