package org.zone.commands.system.arguments.zone;

import net.kyori.adventure.text.Component;
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

public class ZoneGroupArgument implements CommandArgument<Group> {

    private final String id;
    private final String zoneKey;

    public ZoneGroupArgument(String id, CommandArgument<Zone> zoneArgument) {
        this(id, zoneArgument.getId());
    }

    public ZoneGroupArgument(String id, String zoneKey) {
        this.id = id;
        this.zoneKey = zoneKey;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<Group> parse(CommandContext context, CommandArgumentContext<Group> argument) throws IOException {
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
    public Collection<CommandCompletion> suggest(CommandContext context, CommandArgumentContext<Group> argument) {
        Zone zone = context.getArgument(argument.getArgumentCommand(), this.zoneKey);
        Set<Group> groups = zone.getMembers().getGroups();
        String target = argument.getFocusArgument().toLowerCase();
        return groups
                .parallelStream()
                .filter(group -> group.getId().toLowerCase().startsWith(target))
                .map(group -> CommandCompletion.of(group.getId(), Component.text(group.getName())))
                .collect(Collectors.toSet());
    }
}
