package org.zone.commands.structure.region.flags.interact.door;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.interact.door.DoorInteractionFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to view the details of {@link DoorInteractionFlag}
 */
public class ZoneFlagInteractDoorViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_DOOR_INTERACTION_VIEW,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .build());

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE,
                new ExactArgument("interact"),
                new ExactArgument("door"),
                OptionalArgument.createArgument(new ExactArgument("view")));
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getInteractDoorViewCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_DOOR_INTERACTION_VIEW);
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        Optional<DoorInteractionFlag> opFlag = zone.getFlag(FlagTypes.DOOR_INTERACTION);
        commandContext.sendMessage(Messages.getEnabledInfo(opFlag.isPresent()));
        zone
                .getMembers()
                .getGroup(GroupKeys.INTERACT_DOOR)
                .ifPresent(group -> commandContext.sendMessage(Messages.getGroupInfo(group)));
        return CommandResult.success();
    }
}
