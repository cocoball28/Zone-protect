package org.zone.commands.structure.zone.flags.interact.door;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.Identifiable;
import org.zone.Permissions;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.door.DoorInteractionFlag;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to view the details of {@link DoorInteractionFlag}
 */
public class ZoneFlagInteractDoorViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId", new ZoneArgument.ZoneArgumentPropertiesBuilder().setLevel(GroupKeys.OWNER));

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("zone"), new ExactArgument("flag"), ZONE, new ExactArgument("interact"), new ExactArgument("door"), new OptionalArgument<>(new ExactArgument("view"), (String) null));
    }

    @Override
    public Component getDescription() {
        return Component.text("View the details on door interaction");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull DoorInteractionFlag flag = zone
                .getFlag(FlagTypes.DOOR_INTERACTION)
                .orElse(new DoorInteractionFlag(DoorInteractionFlag.ELSE));
        commandContext.getCause().sendMessage(Identity.nil(), Component.text("Enabled: " + flag.isEnabled()));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Component.text("Group: " +
                        zone.getMembers().getGroup(GroupKeys.INTERACT_DOOR).map(Identifiable::getName).orElse("None")));
        return CommandResult.success();
    }
}
