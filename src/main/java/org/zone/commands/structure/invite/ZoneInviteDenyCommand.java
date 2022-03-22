package org.zone.commands.structure.invite;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.request.join.JoinRequestFlag;
import org.zone.utils.Messages;

import java.util.*;

public class ZoneInviteDenyCommand implements ArgumentCommand {

    public static final RemainingArgument<Zone> ZONE_ID = new RemainingArgument<>(new ZoneArgument(
            "zoneId",
            ZonePermissions.OVERRIDE_FLAG_INVITE_DENY,
            new ZoneArgumentFilterBuilder().setFilter((zone, context) -> {
                Subject subject = context.getSource();
                if (!(subject instanceof Player player)) {
                    return false;
                }
                @NotNull Optional<JoinRequestFlag> opFlag = zone.getFlag(FlagTypes.JOIN_REQUEST);
                return opFlag.isPresent() && opFlag.get().getInvites().contains(player.uniqueId());
            }).build()));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("invite"), new ExactArgument("accept"), ZONE_ID);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getZoneInviteDenyCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_INVITE_DENY);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        if (!(commandContext.getSource() instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }
        List<Zone> zones = commandContext.getArgument(this, ZONE_ID);
        zones.forEach(zone -> {
            Optional<JoinRequestFlag> opJoinRequestFlag = zone.getFlag(FlagTypes.JOIN_REQUEST);
            Collection<UUID> invites = opJoinRequestFlag
                    .map(JoinRequestFlag::getInvites)
                    .orElse(Collections.emptySet());
            if (!(invites.contains(player.uniqueId()))) {
                return;
            }
            invites.remove(player.uniqueId());
            zone.setFlag(opJoinRequestFlag.get());
            try {
                zone.save();
                commandContext.sendMessage(Messages.getInvitationDenied(zone));
            } catch (ConfigurateException ce) {
                ce.printStackTrace();
                commandContext.sendMessage(Messages.getZoneSavingError(ce));
            }
        });
        return CommandResult.success();
    }
}
