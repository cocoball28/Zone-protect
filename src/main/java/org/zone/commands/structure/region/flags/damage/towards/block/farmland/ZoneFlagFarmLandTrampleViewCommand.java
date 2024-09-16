package org.zone.commands.structure.region.flags.damage.towards.block.farmland;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagFarmLandTrampleViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_FARMLAND_TRAMPLE_VIEW,
            new ZoneArgumentFilterBuilder().setFilter(ZoneArgumentFilters.MEMBERS_ONLY).build());

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_ID,
                             new ExactArgument("farmland"),
                             new ExactArgument("trample"),
                             new ExactArgument("view"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getFarmLandTrampleViewCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_FARMLAND_TRAMPLE_VIEW);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                        Messages.getEnabledInfo(zone.containsFlag(FlagTypes.FARM_TRAMPLING)));
        return CommandResult.success();
    }
}
