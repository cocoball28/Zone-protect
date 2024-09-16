package org.zone.commands.structure.region.flags.damage.towards.both.tnt;

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
import org.zone.region.flag.entity.nonliving.block.tnt.TnTDefuseFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagTntDefuseSetEnableCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_TNT_DEFUSE_ENABLE,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .setPermission(ZonePermissions.FLAG_TNT_DEFUSE_ENABLE)
                    .build());
    public static final BooleanArgument ENABLE_DISABLE = new BooleanArgument("enabledValue",
            "enable",
            "disable");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_VALUE,
                new ExactArgument("tnt"),
                new ExactArgument("defuse"),
                new ExactArgument("set"),
                ENABLE_DISABLE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getTntDefuseEnableCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_TNT_DEFUSE_ENABLE);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        boolean enable = commandContext.getArgument(this, ENABLE_DISABLE);
        TnTDefuseFlag tnTDefuseFlag = zone
                .getFlag(FlagTypes.TNT_DEFUSE)
                .orElse(new TnTDefuseFlag());
        if (enable) {
            zone.addFlag(tnTDefuseFlag);
        } else {
            zone.removeFlag(FlagTypes.TNT_DEFUSE);
        }
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.TNT_DEFUSE));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
