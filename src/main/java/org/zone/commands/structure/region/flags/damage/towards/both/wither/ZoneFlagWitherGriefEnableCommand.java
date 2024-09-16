package org.zone.commands.structure.region.flags.damage.towards.both.wither;

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
import org.zone.region.flag.entity.monster.block.explode.wither.WitherGriefFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagWitherGriefEnableCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID =
            new ZoneArgument("zoneId",
                    ZonePermissions.OVERRIDE_FLAG_WITHER_GRIEF_ENABLE,
                    new ZoneArgumentFilterBuilder()
                            .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                            .build());
    public static final BooleanArgument ENABLE = new BooleanArgument("enableValue",
            "enable",
            "disable");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_ID,
                new ExactArgument("grief"),
                new ExactArgument("wither"),
                new ExactArgument("set"),
                ENABLE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getWitherGriefEnableCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_WITHER_GRIEF_ENABLE);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        boolean enable = commandContext.getArgument(this, ENABLE);
        WitherGriefFlag witherGriefFlag = zone
                .getFlag(FlagTypes.WITHER_GRIEF)
                .orElse(FlagTypes.WITHER_GRIEF.createCopyOfDefault());
        if (enable) {
            zone.addFlag(witherGriefFlag);
        } else {
            zone.removeFlag(FlagTypes.WITHER_GRIEF);
        }
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.WITHER_GRIEF));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
