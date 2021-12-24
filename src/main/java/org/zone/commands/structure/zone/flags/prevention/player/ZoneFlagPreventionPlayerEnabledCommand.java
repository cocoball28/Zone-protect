package org.zone.commands.structure.zone.flags.prevention.player;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.greetings.GreetingsFlag;
import org.zone.region.flag.move.player.PreventPlayersFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagPreventionPlayerEnabledCommand implements ArgumentCommand {
    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAG = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value", new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ExactArgument PLAYER = new ExactArgument("player");
    public static final ExactArgument PREVENTION = new ExactArgument("prevention");
    public static final ExactArgument ENABLE = new ExactArgument("enable");

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAG, ZONE_VALUE, PLAYER, PREVENTION, ENABLE);
    }

    @Override
    public Component getDescription() {
        return Component.text("Command to enable Player Prevention");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String[] args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        PreventPlayersFlag preventPlayersFlag = zone.getFlag(FlagTypes.PREVENT_PLAYERS_FLAG_TYPE).orElse(new PreventPlayersFlag());
        preventPlayersFlag.setEnabled(@Nullable Boolean PreventPlayersFlag);
        return null;
    }
}
