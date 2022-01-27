package org.zone.commands.structure.region.flags.damage.tnt;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.nonliving.tnt.TnTDefuseFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagTntDefuseSetEnableDisableCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_FLAG_TNT_DEFUSE_ENABLE));
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
        return Component.text("Enable or disable the tnt defuse flag");
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
                .getFlag(FlagTypes.TNT_DEFUSE_FLAG_TYPE)
                .orElse(new TnTDefuseFlag());
        if (enable) {
            zone.addFlag(tnTDefuseFlag);
        } else {
            zone.removeFlag(FlagTypes.TNT_DEFUSE_FLAG_TYPE);
        }
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.TNT_DEFUSE_FLAG_TYPE));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
