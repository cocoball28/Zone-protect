package org.zone.commands.structure.invite;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.sponge.UserArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.invite.InviteFlag;
import org.zone.region.flag.meta.service.ban.flag.BanFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ZoneInvitePlayerCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_INVITE_PLAYER,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .build());
    public static final RemainingArgument<GameProfile> USERS = new RemainingArgument<>(new UserArgument(
            "users"));
    public static final OptionalArgument<Optional<String>> CONFIRM =
            OptionalArgument.createArgument(new ExactArgument("confirm"), true);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("invite"),
                             ZONE_ID,
                             USERS,
                             CONFIRM);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getZoneInvitePlayerCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_INVITE_PLAYER);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        List<GameProfile> players = commandContext.getArgument(this, USERS);
        boolean wasConfirmed = commandContext.getArgument(this, CONFIRM).isPresent();
        BanFlag banFlag = zone.getFlag(FlagTypes.BAN).orElse(new BanFlag());
        InviteFlag inviteFlag = zone
                .getFlag(FlagTypes.INVITE)
                .orElse(new InviteFlag());
        if (wasConfirmed) {
            players
                    .forEach(profile -> {
                        if (banFlag.isBanned(profile.uniqueId())) {
                            commandContext.sendMessage(Messages.getBannedWarning(profile.name().orElse(null)));
                        }
                    });
        }
        inviteFlag
                .registerInvites(players
                        .stream()
                        .map(GameProfile::uuid)
                        .collect(Collectors.toList()));
        zone.setFlag(inviteFlag);
        try {
            zone.save();
            players
                    .stream()
                    .map(profile -> Sponge.server().player(profile.uniqueId()).orElse(null))
                    .filter(Objects::nonNull)
                    .forEach(player -> player.sendMessage(Messages.getGotInvite(player, zone)));
            commandContext.sendMessage(Messages.getInvitedPlayer());
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
