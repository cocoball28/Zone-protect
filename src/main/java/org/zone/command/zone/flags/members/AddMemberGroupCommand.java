package org.zone.command.zone.flags.members;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.client.LocalServer;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.user.UserManager;
import org.zone.Identifiable;
import org.zone.Permissions;
import org.zone.command.ZoneArguments;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public final class AddMemberGroupCommand {

    public static final class Executor implements CommandExecutor {

        private void handleUserManager(CommandContext context, Zone zone, Group group, UUID uuid, UserManager manager) {
            manager.load(uuid).thenAccept(opUser -> {
                if (opUser.isEmpty()) {
                    context.sendMessage(Identity.nil(), Component.text("Invalid user").color(NamedTextColor.RED));
                    return;
                }
                this.addToGroup(context, zone, group, opUser.get());
            });
        }

        private void addToGroup(CommandContext context, Zone zone, Group group, User user) {
            Group previous = zone.getMembers().getGroup(user.uniqueId());
            if (!previous.equals(SimpleGroup.VISITOR)) {
                zone.getMembers().removeMember(user.uniqueId());
            }
            context.sendMessage(Identity.nil(),
                    Component.text("Moved " + user.name() + " from " + previous.getName() + " to " + group.getName()));
            if (user.isOnline()) {
                user.player().ifPresent(player ->
                        player.sendMessage(Identity.nil(),
                                Component.text("You have been moved in '" + zone.getName() + "' from '" + previous.getName() + "' to '" + group.getName() +
                                        "'"))
                );
            }

            zone.getMembers().addMember(group, user.uniqueId());
            try {
                zone.save();
            } catch (IOException e) {
                context.sendMessage(Identity.nil(), Component.text("Could not save zone. Console log will show" +
                        " more " +
                        "info on " +
                        "why").color(NamedTextColor.RED));
            }
        }

        @Override
        public CommandResult execute(CommandContext context) throws CommandException {
            Optional<Zone> opZone = context.one(ZONE);
            Optional<Group> opGroup = context.one(GROUP);
            Optional<UUID> opUUID = context.one(USER);
            if (opZone.isEmpty() || opGroup.isEmpty() || opUUID.isEmpty()) {
                throw new CommandException(Component.text("Zone or Group is missing... wait... this isn't right"));
            }
            Zone zone = opZone.get();
            Group group = opGroup.get();
            UUID uuid = opUUID.get();

            if (Sponge.isServerAvailable()) {
                this.handleUserManager(context, zone, group, uuid, Sponge.server().userManager());
                return CommandResult.success();
            } else {
                Optional<LocalServer> opServer = Sponge.client().server();
                if (opServer.isPresent()) {
                    {
                        this.handleUserManager(context, zone, group, uuid,
                                opServer.get().userManager());
                        return CommandResult.success();
                    }
                }
            }
            return CommandResult.error(Component.text("Could not process request. You must be on a server or 'open " +
                    "to LAN' to run this command."));
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
            })).key("zone").build();

    public static final Parameter.Value<Group> GROUP =
            Parameter
                    .builder(Group.class)
                    .key("group")
                    .addParser((parameterKey, reader, context) -> {
                        Optional<Zone> opZone = context.one(ZONE);
                        if (opZone.isEmpty()) {
                            return Optional.empty();
                        }
                        Zone zone = opZone.get();
                        String arg = reader.parseUnquotedString();
                        return zone
                                .getMembers()
                                .getGroups()
                                .parallelStream()
                                .filter(group -> group.getId().equalsIgnoreCase(arg))
                                .filter(group -> !group.equals(SimpleGroup.VISITOR))
                                .findFirst();
                    })
                    .completer((context, currentInput) -> {
                        Optional<Zone> opZone = context.one(ZONE);
                        if (opZone.isEmpty()) {
                            return Collections.singletonList(CommandCompletion.of(currentInput,
                                    Component
                                            .text("Unknown Zone")
                                            .color(NamedTextColor.RED)));
                        }
                        Zone zone = opZone.get();
                        return zone
                                .getMembers()
                                .getGroups()
                                .parallelStream()
                                .filter(group ->
                                        group.getId().toLowerCase().contains(currentInput.toLowerCase()) ||
                                                group.getName().toLowerCase().contains(currentInput.toLowerCase()))
                                .filter(group -> !group.equals(SimpleGroup.VISITOR))
                                .sorted(Comparator.comparing(Identifiable::getId))
                                .map(group -> CommandCompletion.of(group.getId(),
                                        Component.text(group.getName()).color(NamedTextColor.AQUA)))
                                .collect(Collectors.toList());
                    })
                    .build();

    public static final Parameter.Value<UUID> USER = Parameter.user().key("player").build();

    private AddMemberGroupCommand() {
        throw new RuntimeException("Should not init");
    }
}
