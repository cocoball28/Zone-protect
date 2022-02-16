package org.zone.commands.structure.join.visibility;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
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

public class ZoneVisibilityViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder()
                    .setBypassSuggestionPermission(ZonePermissions.OVERRIDE_ZONE_VISIBILITY_VIEW));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("visibility"),
                ZONE_ID,
                new ExactArgument("view"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("View the visibility of a zone");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.ZONE_VISIBILITY_VIEW);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        ZoneVisibility zoneVisibility = zone
                .getFlag(FlagTypes.ZONE_VISIBILITY)
                .map(ZoneVisibilityFlag::getZoneVisibility)
                .orElse(ZoneVisibility.PUBLIC);
        String visibilityName = zoneVisibility.toString();
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                        Messages.getZoneVisibility(visibilityName));
        return CommandResult.success();
    }
}
