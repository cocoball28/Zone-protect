package org.zone.commands.system.arguments.zone.filter;

import org.jetbrains.annotations.NotNull;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;

import java.util.Optional;

public interface ZoneArgumentFilter {

    boolean mustHappen();

    @NotNull Optional<ZonePermission> getPermission();

    boolean accepts(@NotNull Zone zone, @NotNull CommandContext context);
}
