package org.zone.commands.structure.zone.flags.interact.door;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.door.DoorInteractionFlag;
import org.zone.region.group.DefaultGroups;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagInteractDoorGroupCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
            new ZoneArgument
                    .ZoneArgumentPropertiesBuilder()
                    .setLevel(GroupKeys.OWNER));

    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupId", ZONE);

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("zone"),
                new ExactArgument("flag"),
                ZONE,
                new ExactArgument("interact"),
                new ExactArgument("door"),
                new ExactArgument("group"),
                GROUP);
    }

    @Override
    public Component getDescription() {
        return Component.text("Sets the minimum group that can interact with doors");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull DoorInteractionFlag flag =
                zone.getFlag(FlagTypes.DOOR_INTERACTION).orElseGet(() -> new DoorInteractionFlag(DoorInteractionFlag.ELSE));
        Group newGroup = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(newGroup, flag.getRequiredKey());
        commandContext.getCause().sendMessage(Identity.nil(), Component.text("Updated Door Interaction"));
        zone.addFlag(flag);
        try {
            zone.save();
            commandContext.getCause().sendMessage(Identity.nil(), Component.text("Updated Door Interaction"));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Could not save: " + e.getMessage()));
        }
        return CommandResult.success();
    }
}
