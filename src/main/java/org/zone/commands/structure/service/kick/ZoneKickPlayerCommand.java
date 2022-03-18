package org.zone.commands.structure.service.kick;

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
import org.zone.commands.system.arguments.sponge.ComponentRemainingArgument;
import org.zone.commands.system.arguments.sponge.UserArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ZoneKickPlayerCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_KICK));
    public static final RemainingArgument<GameProfile> USERS =
            new RemainingArgument<>(new UserArgument("users"));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             ZONE_ID,
                             new ExactArgument("kick"),
                             USERS);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getKickPlayerCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.KICK);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        List<GameProfile> players = commandContext.getArgument(this, USERS);
        MembersFlag membersFlag = zone.getMembers();
        players.forEach(player -> membersFlag.removeMember(player.uniqueId()));
        players
                .stream()
                .map(profile -> Sponge
                        .server()
                        .player(profile.uniqueId())
                        .orElse(null))
                .filter(Objects::nonNull)
                .forEach(sPlayer -> sPlayer.sendMessage(Messages.getGotKickedFromZone(zone)));
        try {
            zone.save();
            commandContext.sendMessage(Messages.getKickedPlayers());
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
