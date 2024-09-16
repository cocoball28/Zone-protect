package org.zone.commands.structure.region.flags.damage.towards.player.fire;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.damage.fire.PlayerFireDamageFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagFireDamageSetEnable implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_FIRE_DAMAGE_ENABLE,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .setPermission(ZonePermissions.FLAG_FIRE_DAMAGE_ENABLE)
                    .build());
    public static final BooleanArgument VALUE = new BooleanArgument("enabledValue",
            "enable",
            "disable");

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
                VALUE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getPlayerFireDamageEnableCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_FIRE_DAMAGE_ENABLE);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        boolean enable = commandContext.getArgument(this, VALUE);
        PlayerFireDamageFlag playerFireDamageFlag = zone
                .getFlag(FlagTypes.PLAYER_FIRE_DAMAGE)
                .orElse(FlagTypes.PLAYER_FIRE_DAMAGE.createCopyOfDefault());
        if (enable) {
            zone.addFlag(playerFireDamageFlag);
        } else {
            zone.removeFlag(FlagTypes.PLAYER_FIRE_DAMAGE);
        }
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.PLAYER_FIRE_DAMAGE));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            commandContext.sendMessage(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
