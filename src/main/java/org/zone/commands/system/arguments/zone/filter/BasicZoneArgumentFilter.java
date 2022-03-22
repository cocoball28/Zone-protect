package org.zone.commands.system.arguments.zone.filter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;

import java.util.Optional;
import java.util.function.BiPredicate;

public class BasicZoneArgumentFilter implements ZoneArgumentFilter {

    private final boolean ignoreGlobalPermission;
    private final @Nullable ZonePermission permission;
    private final @NotNull BiPredicate<Zone, CommandContext> predicate;

    public BasicZoneArgumentFilter(@NotNull ZoneArgumentFilterBuilder builder) {
        this.ignoreGlobalPermission = builder.shouldRunWithoutGlobalPermissionCheck();
        this.permission = builder.getPermission().orElse(null);
        this.predicate = builder.getFilter();
        if (this.predicate == null) {
            throw new IllegalArgumentException("Builder must contain a filter");
        }
    }

    @Override
    public boolean mustHappen() {
        return this.ignoreGlobalPermission;
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermission() {
        return Optional.ofNullable(this.permission);
    }

    @Override
    public boolean accepts(
            @NotNull Zone zone, @NotNull CommandContext context) {
        return this.predicate.test(zone, context);
    }
}
