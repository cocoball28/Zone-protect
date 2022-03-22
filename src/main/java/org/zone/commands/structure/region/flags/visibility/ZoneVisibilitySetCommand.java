package org.zone.commands.structure.region.flags.visibility;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.EnumArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.request.visibility.ZoneVisibility;
import org.zone.region.flag.meta.request.visibility.ZoneVisibilityFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneVisibilitySetCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_ZONE_VISIBILITY_SET));
    public static final EnumArgument<ZoneVisibility> VALUE = new EnumArgument<>("visibility_name",
            ZoneVisibility.class);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("visibility"),
                ZONE_ID,
                new ExactArgument("set"),
                VALUE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getZoneVisibilitySetCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.ZONE_VISIBILITY_SET);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        ZoneVisibility zoneVisibility = commandContext.getArgument(this, VALUE);
        ZoneVisibilityFlag zoneVisibilityFlag = zone
                .getFlag(FlagTypes.ZONE_VISIBILITY)
                .orElse(new ZoneVisibilityFlag());
        zoneVisibilityFlag.setZoneVisibility(zoneVisibility);
        zone.setFlag(zoneVisibilityFlag);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getUpdatedMessage(FlagTypes.ZONE_VISIBILITY));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
