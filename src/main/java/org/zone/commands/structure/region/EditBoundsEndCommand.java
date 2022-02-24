package org.zone.commands.structure.region;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.math.vector.Vector3i;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.edit.EditingFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EditBoundsEndCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_FLAG_EDIT_BOUNDS_END));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("edit"),
                             ZONE_ID,
                             new ExactArgument("bounds"),
                             new ExactArgument("end"));
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("End editing the size of the region");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_EDIT_BOUNDS_END);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        if (zone.getFlag(FlagTypes.EDITING).isEmpty()) {
            return CommandResult.error(Messages.getZoneNotBeingEdited());
        }
        EditingFlag editingFlag = zone.getFlag(FlagTypes.EDITING).get();
        Vector3i newPos = editingFlag.getNewPosition();
        editingFlag.getRegion().setPosition(editingFlag.getPositionType(), newPos);
        zone.removeFlag(FlagTypes.EDITING);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getEditedZone());
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
