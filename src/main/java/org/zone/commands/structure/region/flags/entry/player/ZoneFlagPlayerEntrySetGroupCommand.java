package org.zone.commands.structure.region.flags.entry.player;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.preventing.PreventPlayersFlag;
import org.zone.region.group.Group;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagPlayerEntrySetGroupCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value",
                                                                   new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupId", ZONE_VALUE);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_VALUE,
                             new ExactArgument("entry"),
                             new ExactArgument("player"),
                             new ExactArgument("set"),
                             new ExactArgument("group"),
                             GROUP);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Sets the group for Player Prevention flag");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext,
                                      @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        PreventPlayersFlag preventPlayersFlag = zone
                .getFlag(FlagTypes.PREVENT_PLAYERS)
                .orElse(FlagTypes.PREVENT_PLAYERS.createCopyOfDefault());
        Group group = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(group, preventPlayersFlag.getRequiredKey());
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.PREVENT_PLAYERS));
        } catch (ConfigurateException ce) {
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
