package org.zone.commands.structure.join;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.request.join.JoinRequestFlag;
import org.zone.region.flag.meta.request.visibility.ZoneVisibility;
import org.zone.region.flag.meta.request.visibility.ZoneVisibilityFlag;
import org.zone.region.flag.meta.service.ban.flag.BanFlag;
import org.zone.region.group.DefaultGroups;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JoinZoneCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder()
                    .setVisitorOnly(true)
                    .setBypassSuggestionPermission(ZonePermissions.OVERRIDE_FLAG_JOIN_ZONE));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("join"), ZONE_ID);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getJoinZoneCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_JOIN_ZONE);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        if (!(commandContext.getSource() instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        if (!(zone.getMembers().getGroup(player.uniqueId()).equals(DefaultGroups.VISITOR))) {
            return CommandResult.error(Messages.getOwnZone());
        }
        JoinRequestFlag joinRequestFlag = zone
                .getFlag(FlagTypes.JOIN_REQUEST)
                .orElse(new JoinRequestFlag());
        ZoneVisibility zoneVisibility = zone
                .getFlag(FlagTypes.ZONE_VISIBILITY)
                .map(ZoneVisibilityFlag::getZoneVisibility)
                .orElse(ZoneVisibility.PUBLIC);
        BanFlag banFlag = zone.getFlag(FlagTypes.BAN).orElse(new BanFlag());
        if (zoneVisibility == ZoneVisibility.PRIVATE || zoneVisibility == ZoneVisibility.SEMI_PRIVATE) {
            return CommandResult.error(Messages.getZonePrivateError());
        }
        if (banFlag.isBanned(player.uniqueId())) {
            return CommandResult.error(Messages.getIsBanned());
        }
        joinRequestFlag.registerJoin(player.uniqueId());
        zone.setFlag(joinRequestFlag);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getJoinedZoneMessage(zone));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
