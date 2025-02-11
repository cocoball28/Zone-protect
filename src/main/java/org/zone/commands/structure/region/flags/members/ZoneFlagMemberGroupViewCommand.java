package org.zone.commands.structure.region.flags.members;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.user.UserManager;
import org.zone.Permissions;
import org.zone.utils.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.simple.number.IntegerArgument;
import org.zone.commands.system.arguments.simple.number.RangeArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.group.Group;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Used to view the member of a zone in {@link org.zone.region.flag.meta.member.MembersFlag}
 */
public class ZoneFlagMemberGroupViewCommand implements ArgumentCommand {

    public final OptionalArgument<Integer> PAGE = new OptionalArgument<>(new RangeArgument<>(new IntegerArgument(
            "page"), (c, a) -> CommandArgumentResult.from(a, 1), (c, a) -> {
        Zone zone = c.getArgument(this, ZONE);
        Group group = c.getArgument(this, GROUP);
        int pages = zone.getMembers().getMembers(group).size() / 10;
        return CommandArgumentResult.from(a, pages);

    }), 1);
    public static final ZoneArgument ZONE = new ZoneArgument("zoneId");
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupId", ZONE);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("members"),
                             ZONE,
                             new ExactArgument("view"),
                             GROUP,
                             this.PAGE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("View the members in a zone by group");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        Group group = commandContext.getArgument(this, GROUP);
        int page = commandContext.getArgument(this, this.PAGE);
        if (page <= 0) {
            return CommandResult.error(Messages.getPageTooLow());
        }

        Collection<UUID> memberIds = zone.getMembers().getMembers(group);
        UserManager userManager = Sponge.server().userManager();
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Messages.getZoneFlagMemberZoneViewCommandMessage1(zone));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Messages.getZoneFlagMemberGroupViewCommand(group));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Messages.getZoneFlagMemberGroupViewCommandTotal(memberIds));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Messages.getZoneFlagMemberGroupViewCommandPages(page));
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
            loader.thenAccept(opUser -> opUser.ifPresent(user -> commandContext
                    .getCause()
                    .sendMessage(Identity.nil(),
                                 Messages.getZoneFlagMemberGroupViewCommandUserName(user))));
        }
        return CommandResult.success();
    }
}
