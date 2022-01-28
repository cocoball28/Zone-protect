package org.zone.commands.structure.region.flags.damage.fire;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagFireDamageView implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(ZonePermissions.OVERRIDE_FLAG_FIRE_DAMAGE_VIEW));
    public static final OptionalArgument<String> VIEW =
            new OptionalArgument<>(new ExactArgument("view"), (String) null);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_ID,
                             new ExactArgument("damage"),
                             new ExactArgument("fire"),
                             new ExactArgument("towards"),
                             new ExactArgument("player"),
                             VIEW);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("View info on the Fire Damage");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_FIRE_DAMAGE_VIEW);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                        Messages.getEnabledInfo(zone.containsFlag(FlagTypes.PLAYER_FIRE_DAMAGE_FLAG_TYPE)));
        zone
                .getMembers()
                .getGroup(GroupKeys.PLAYER_FIRE_DAMAGE)
                .ifPresent(group -> commandContext.sendMessage(Messages.getGroupInfo(group)));
        return CommandResult.success();
    }
}
