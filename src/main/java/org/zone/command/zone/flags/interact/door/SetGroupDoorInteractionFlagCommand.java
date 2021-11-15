package org.zone.command.zone.flags.interact.door;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.Permissions;
import org.zone.command.ZoneArguments;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.door.DoorInteractionFlag;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class SetGroupDoorInteractionFlagCommand {

    public static class Executor implements CommandExecutor {

        @Override
        public CommandResult execute(CommandContext context) {
            Zone zone = context.requireOne(ZONE);
            @NotNull DoorInteractionFlag flag =
                    zone.getFlag(FlagTypes.DOOR_INTERACTION).orElseGet(() -> new DoorInteractionFlag(DoorInteractionFlag.ELSE));
            Group newGroup = context.requireOne(VALUE);
            flag.setGroup(newGroup);
            context.sendMessage(Identity.nil(), Component.text("Updated Door Interaction"));
            zone.addFlag(flag);
            try {
                zone.save();
                context.sendMessage(Identity.nil(), Component.text("Updated Door Interaction"));
            } catch (ConfigurateException e) {
                e.printStackTrace();
                return CommandResult.error(Component.text("Could not save: " + e.getMessage()));
            }
            return CommandResult.success();
        }
    }

    public static final Parameter.Value<Zone> ZONE = ZoneArguments
            .createZoneArgument((stream, context) -> stream.filter(zone -> {
                Subject subject = context.subject();
                if (!(subject instanceof Player p)) {
                    return zone.getParent().isEmpty();
                }
                if (context.hasPermission(Permissions.REGION_ADMIN_INFO.getPermission()) && zone.getParent().isEmpty()) {
                    return true;
                }

                Group group = zone.getMembers().getGroup(p.uniqueId());

                return group.equals(SimpleGroup.OWNER);
            }))
            .key("zone")
            .build();

    public static final Parameter.Value<Group> VALUE = Parameter
            .builder(Group.class)
            .addParser((parameterKey, reader, context) -> {
                String compare = reader.parseUnquotedString();
                Optional<Zone> opZone = context.one(ZONE);
                if (opZone.isEmpty()) {
                    throw reader.createException(Component.text("Unknown zone"));
                }
                Zone zone = opZone.get();
                return zone
                        .getMembers()
                        .getGroups()
                        .parallelStream()
                        .filter(group -> group.getId().equals(compare))
                        .findAny();
            })
            .completer((context, currentInput) -> {
                Optional<Zone> opZone = context.one(ZONE);
                if (opZone.isEmpty()) {
                    return Collections.emptyList();
                }
                return opZone
                        .get()
                        .getMembers()
                        .getGroups()
                        .parallelStream()
                        .map(g -> CommandCompletion.of(g.getId(), Component.text(g.getName())))
                        .collect(Collectors.toList());
            })
            .key("group")
            .build();
}
