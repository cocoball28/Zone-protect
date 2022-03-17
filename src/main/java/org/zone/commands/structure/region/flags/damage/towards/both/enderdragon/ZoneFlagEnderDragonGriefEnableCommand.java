package org.zone.commands.structure.region.flags.damage.towards.both.enderdragon;

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
import org.zone.region.flag.entity.monster.block.explode.enderdragon.EnderDragonGriefFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagEnderDragonGriefEnableCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder()
                    .setBypassSuggestionPermission(ZonePermissions.OVERRIDE_FLAG_ENDERDRAGON_GRIEF_ENABLE));
    public static final BooleanArgument ENABLE = new BooleanArgument("enableValue",
            "enable",
            "disable");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_ID,
                new ExactArgument("grief"),
                new ExactArgument("enderdragon"),
                new ExactArgument("set"),
                ENABLE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getEnderDragonGriefEnableCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_ENDERDRAGON_GRIEF_ENABLE);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        boolean enable = commandContext.getArgument(this, ENABLE);
        EnderDragonGriefFlag enderDragonGriefFlag = zone
                .getFlag(FlagTypes.ENDER_DRAGON_GRIEF)
                .orElse(FlagTypes.ENDER_DRAGON_GRIEF.createCopyOfDefault());
        if (enable) {
            zone.addFlag(enderDragonGriefFlag);
        } else {
            zone.removeFlag(FlagTypes.ENDER_DRAGON_GRIEF);
        }
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.ENDER_DRAGON_GRIEF));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
