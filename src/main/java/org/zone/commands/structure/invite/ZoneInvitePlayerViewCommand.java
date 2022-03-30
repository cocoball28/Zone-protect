package org.zone.commands.structure.invite;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.profile.GameProfile;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.invite.InviteFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.*;
import java.util.stream.Collectors;

public class ZoneInvitePlayerViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_INVITE_PLAYER_VIEW,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .setPermission(ZonePermissions.FLAG_INVITE_PLAYER_VIEW)
                    .build());

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("invite"),
                ZONE_ID,
                new ExactArgument("view"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getZoneInvitePlayerViewCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_INVITE_PLAYER_VIEW);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        Optional<InviteFlag> opInviteFlag = zone
                .getFlag(FlagTypes.INVITE);
        if (opInviteFlag.isEmpty()) {
            return CommandResult.error(Messages.getInviteFlagNotFound());
        }
        Collection<UUID> inviteUUIDs = opInviteFlag.get().getInvites();
        List<GameProfile> profiles = Sponge.server().gameProfileManager().cache().stream().sorted().toList();

        Collection<GameProfile> inviteProfiles = profiles
                .stream()
                .filter(profile -> inviteUUIDs.contains(profile.uniqueId()))
                .collect(Collectors.toSet());

        commandContext.sendMessage(Messages.getInvitesPlayersTag());
        inviteProfiles.forEach(profile -> commandContext.sendMessage(Messages.getEntry(profile
                .name()
                .orElse("Unknown Username"))));
        return CommandResult.success();
    }
}
