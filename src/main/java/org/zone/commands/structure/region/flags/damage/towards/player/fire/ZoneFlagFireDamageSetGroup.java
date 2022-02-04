package org.zone.commands.structure.region.flags.damage.towards.player.fire;

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
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.damage.fire.PlayerFireDamageFlag;
import org.zone.region.group.Group;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagFireDamageSetGroup implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_FLAG_FIRE_DAMAGE_SET_GROUP));
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupId", ZONE_ID);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_ID,
                             new ExactArgument("damage"),
                             new ExactArgument("fire"),
                             new ExactArgument("towards"),
                             new ExactArgument("player"),
                             new ExactArgument("set"),
                             GROUP);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Set the Group Key for Fire Damage");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_FIRE_DAMAGE_SET_GROUP);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        PlayerFireDamageFlag playerFireDamageFlag = zone
                .getFlag(FlagTypes.PLAYER_FIRE_DAMAGE)
                .orElse(FlagTypes.PLAYER_FIRE_DAMAGE.createCopyOfDefault());
        Group group = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(group, playerFireDamageFlag.getRequiredKey());
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.PLAYER_FIRE_DAMAGE));
        }catch (ConfigurateException ce) {
            ce.printStackTrace();
            commandContext.sendMessage(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
