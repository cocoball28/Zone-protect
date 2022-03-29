package org.zone.commands.system.arguments.zone.filter;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKey;

import java.util.function.BiPredicate;

public class WithGroupKeyZoneFilter implements BiPredicate<Zone, CommandContext> {

    private final @NotNull GroupKey key;

    public WithGroupKeyZoneFilter(@NotNull GroupKey key) {
        this.key = key;
    }


    @Override
    public boolean test(Zone zone, CommandContext context) {
        @NotNull Subject subject = context.getSource();
        if (!(subject instanceof Player player)) {
            return true;
        }
        Group group = zone.getMembers().getGroup(player.uniqueId());
        return group.contains(this.key);
    }
}
