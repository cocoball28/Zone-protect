package org.zone.command.zone.flags.members;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.Sponge;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class ViewMemberGroupCommand {

    public static class Executor implements CommandExecutor {

        @Override
        public CommandResult execute(CommandContext context) throws CommandException {
            Optional<Zone> opZone = context.one(ZONE);
            Optional<Group> opGroup = context.one(GROUP);
            if (opZone.isEmpty() || opGroup.isEmpty()) {
                throw new CommandException(Component.text("Zone or Group is missing... wait... this isn't right"));
            }
            int page = context.one(PAGE).orElse(1);
            if (page <= 0) {
                throw new CommandException(Component.text("Page needs to be 1 or more"));
            }

            Zone zone = opZone.get();
            Group group = opGroup.get();
            Collection<UUID> memberIds = zone.getMembers().getMembers(group);
            UserManager userManager = Sponge.server().userManager();
            context.sendMessage(Identity.nil(),
                    Component.text("----====[").color(NamedTextColor.RED).append(Component.text(zone.getName()).color(NamedTextColor.AQUA).append(Component.text("]====----").color(NamedTextColor.RED))));
            context.sendMessage(Identity.nil(),
                    Component.text("Group: ").color(NamedTextColor.GOLD).append(Component.text(group.getName()).color(NamedTextColor.AQUA)));
            context.sendMessage(Identity.nil(),
                    Component.text("Total: ").color(NamedTextColor.GOLD).append(Component.text(memberIds.size()).color(NamedTextColor.AQUA)));
            context.sendMessage(Identity.nil(),
                    Component.text("Page: ").color(NamedTextColor.GOLD).append(Component.text(page).color(NamedTextColor.AQUA)));
            int count = 0;
            int pageStart = (page - 1) * 10;
            int pageEnd = (page) * 10;
            for (UUID uuid : memberIds) {
                count++;
                if (count < pageStart) {
                    continue;
                }
                if (count > pageEnd) {
                    break;
                }
                CompletableFuture<Optional<User>> loader = userManager.load(uuid);
                loader.thenAccept(opUser -> opUser.ifPresent(user -> context.sendMessage(Identity.nil(),
                        Component.text("- " + user.name()).color(NamedTextColor.AQUA))));
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
                return zone
                        .getMembers()
                        .getGroup(p.uniqueId())
                        .equals(SimpleGroup.OWNER);
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

    public static final Parameter.Value<Integer> PAGE = Parameter.integerNumber().key("page").optional().build();


    private ViewMemberGroupCommand() {
        throw new RuntimeException("Should not be init");
    }
}
