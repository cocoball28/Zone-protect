package org.zone.commands.system.arguments.zone;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.group.Group;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gets a Zone group from a command
 *
 * @since 1.0.0
 */
public class ZoneGroupArgument implements CommandArgument<Group> {

    private final @NotNull String id;
    private final @NotNull String zoneKey;

    /**
     * Creates a zone group argument -> Note that groups are specific to zones and therefore the
     * zone must be known before parsing the group, therefore it should be passed in the command
     *
     * @param id           The id to use for the argument
     * @param zoneArgument The argument of the zone
     * @since 1.0.0
     */
    public ZoneGroupArgument(@NotNull String id, @NotNull CommandArgument<Zone> zoneArgument) {
        this(id, zoneArgument.getId());
    }

    @Deprecated(since = "1.0.1", forRemoval = true)
    public ZoneGroupArgument(@NotNull String id, @NotNull String zoneKey) {
        this.id = id;
        this.zoneKey = zoneKey;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull CommandArgumentResult<Group> parse(
            @NotNull CommandContext context, @NotNull CommandArgumentContext<Group> argument) throws
            IOException {
        Zone zone = context.getArgument(argument.getArgumentCommand(), this.zoneKey);
        Set<Group> groups = zone.getMembers().getGroups();
        String target = argument.getFocusArgument();
        Group ret = groups
                .parallelStream()
                .filter(group -> group.getId().equalsIgnoreCase(target))
                .findAny()
                .orElseThrow(() -> new IOException("Could not find group of " + target));
        return CommandArgumentResult.from(argument, ret);
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext,
            @NotNull CommandArgumentContext<Group> argument) {
        Zone zone = commandContext.getArgument(argument.getArgumentCommand(), this.zoneKey);
        Set<Group> groups = zone.getMembers().getGroups();
        String target = argument.getFocusArgument().toLowerCase();
        return groups
                .parallelStream()
                .filter(group -> group.getId().toLowerCase().startsWith(target))
                .map(group -> CommandCompletion.of(group.getId(), Component.text(group.getName())))
                .collect(Collectors.toSet());
    }
}
