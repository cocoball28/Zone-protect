package org.zone.commands.structure.service.ban;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.simple.TimeUnitArgument;
import org.zone.commands.system.arguments.simple.number.RangeArgument;
import org.zone.commands.system.arguments.sponge.UserArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.flag.meta.service.ban.flag.BanFlag;
import org.zone.utils.Messages;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ZoneTempBanPlayerCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_TEMP_BAN));
    public static final RemainingArgument<GameProfile> USERS =
            new RemainingArgument<>(new UserArgument("users"));
    public static final RangeArgument<Integer> AMOUNT = RangeArgument.createArgument("amount",
            0, Integer.MAX_VALUE);
    public static final TimeUnitArgument UNIT = new TimeUnitArgument("unit");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             ZONE_ID,
                             new ExactArgument("ban"),
                             new ExactArgument("temp"),
                             USERS,
                             AMOUNT,
                             UNIT);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getTempBanPlayerCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.TEMP_BAN);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        List<GameProfile> players = commandContext.getArgument(this, USERS);
        long amount = commandContext.getArgument(this, AMOUNT);
        TemporalUnit unit = commandContext.getArgument(this, UNIT);
        LocalDateTime releaseTime = LocalDateTime.now().plus(amount, unit);
        MembersFlag membersFlag = zone.getMembers();
        BanFlag banFlag = zone.getFlag(FlagTypes.BAN).orElse(new BanFlag());
        players.forEach(profile -> {
            membersFlag.removeMember(profile.uniqueId());
            banFlag.banPlayer(profile.uniqueId(), releaseTime);
        });
        players
                .stream()
                .map(profile -> Sponge
                        .server()
                        .player(profile.uniqueId())
                        .orElse(null))
                .filter(Objects::nonNull)
                .forEach(sPlayer -> sPlayer.sendMessage(Messages.getGotTemporarilyBannedFromZone(zone, releaseTime)));
        try {
            zone.save();
            commandContext.sendMessage(Messages.getTemporarilyBannedPlayers());
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
