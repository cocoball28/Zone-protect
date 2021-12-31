package org.zone.commands.structure.region.flags.prevention.player;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.move.player.preventing.PreventPlayersFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagPreventionPlayerEnableDisableCommand implements ArgumentCommand {
    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAG = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value", new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ExactArgument PLAYER = new ExactArgument("player");
    public static final ExactArgument PREVENTION = new ExactArgument("prevention");
    public static final BooleanArgument ENABLE = new BooleanArgument("enableValue", "enable", "disable");
    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAG, ZONE_VALUE, PLAYER, PREVENTION, ENABLE);
    }

    @Override
    public Component getDescription() {
        return Component.text("Command to enable/disable Player Prevention");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String[] args) {
        boolean enable = commandContext.getArgument(this, ENABLE);
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        PreventPlayersFlag preventPlayersFlag = zone.getFlag(FlagTypes.PREVENT_PLAYERS).orElse(new PreventPlayersFlag());
        preventPlayersFlag.setEnabled(enable);
        zone.setFlag(preventPlayersFlag);
        try {
            zone.save();
        }catch (ConfigurateException ce) {
            ce.printStackTrace();
            commandContext.sendMessage(Messages.getZoneSavingError(ce));
        }

        return CommandResult.success();
    }
}
