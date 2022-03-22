package org.zone.commands.system.arguments.zone.filter;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.group.DefaultGroups;
import org.zone.region.group.key.GroupKey;

import java.util.function.BiPredicate;

public interface ZoneArgumentFilters {

    BiPredicate<Zone, CommandContext> MAIN_ONLY = (zone, context) -> zone.getParentId().isEmpty();
    BiPredicate<Zone, CommandContext> MEMBERS_ONLY = (zone, context) -> !(context.getSource() instanceof Player player) ||
            !zone.getMembers().getGroup(player.uniqueId()).equals(DefaultGroups.VISITOR);
    BiPredicate<Zone, CommandContext> VISITORS_ONLY = (zone, context) -> !(context.getSource() instanceof Player player) ||
            zone.getMembers().getGroup(player.uniqueId()).equals(DefaultGroups.VISITOR);

    static BiPredicate<Zone, CommandContext> withGroupKey(@NotNull GroupKey key) {
        return new WithGroupKeyZoneFilter(key);
    }
}
