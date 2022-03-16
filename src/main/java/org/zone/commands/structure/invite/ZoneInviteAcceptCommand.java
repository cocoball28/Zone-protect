package org.zone.commands.structure.invite;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.request.join.JoinRequestFlag;
import org.zone.region.group.DefaultGroups;
import org.zone.utils.Messages;

import java.util.*;

public class ZoneInviteAcceptCommand implements ArgumentCommand {

    public static final RemainingArgument<Zone> ZONE_ID =
            new RemainingArgument<>(new ZoneArgument("zoneId",
                    new ZoneArgument.ZoneArgumentPropertiesBuilder()
                            .setBypassSuggestionPermission(ZonePermissions.OVERRIDE_FLAG_INVITE_ACCEPT)));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("invite"),
                             new ExactArgument("accept"),
                             ZONE_ID);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getZoneInviteAcceptCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_INVITE_ACCEPT);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        if (!(commandContext.getSource() instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }
        List<Zone> zones = commandContext.getArgument(this, ZONE_ID);
        zones
                .forEach(zone -> {
                    Optional<JoinRequestFlag> opJoinRequestFlag = zone
                            .getFlag(FlagTypes.JOIN_REQUEST);
                    Collection<UUID> invites = opJoinRequestFlag
                            .map(JoinRequestFlag::getInvites)
                            .orElse(Collections.emptySet());
                    if (!(invites.contains(player.uniqueId()))) {
                        return;
                    }
                    zone.getMembers().addMember(DefaultGroups.MEMBER, player.uniqueId());
                    invites.remove(player.uniqueId());
                    zone.setFlag(opJoinRequestFlag.get());
                    try {
                        zone.save();
                        commandContext
                                .sendMessage(Messages.getJoinedZoneMessage(zone));
                    } catch (ConfigurateException ce) {
                        ce.printStackTrace();
                        commandContext.sendMessage(Messages.getZoneSavingError(ce));
                    }
                });
        return CommandResult.success();
    }
}
