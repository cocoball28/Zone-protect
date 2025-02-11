package org.zone.commands.structure.region.flags.entry;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.preventing.PreventPlayersFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagEntrySetEnabledCommand implements ArgumentCommand {
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value",
                                                                   new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final BooleanArgument ENABLE = new BooleanArgument("enableValue",
                                                                     "enable",
                                                                     "disable");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_VALUE,
                             new ExactArgument("entry"),
                             new ExactArgument("set"),
                             ENABLE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Command to enable/disable Player Prevention");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext,
                                      @NotNull String... args) {
        boolean enable = commandContext.getArgument(this, ENABLE);
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);

        PreventPlayersFlag preventPlayersFlag = zone
                .getFlag(FlagTypes.PREVENT_PLAYERS)
                .orElse(new PreventPlayersFlag());
        if (enable) {
            zone.addFlag(preventPlayersFlag);
        } else {
            zone.removeFlag(FlagTypes.PREVENT_PLAYERS);
        }
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.PREVENT_PLAYERS));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            commandContext.sendMessage(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
