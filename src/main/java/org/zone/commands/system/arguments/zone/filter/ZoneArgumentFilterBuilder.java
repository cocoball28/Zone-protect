package org.zone.commands.system.arguments.zone.filter;

import org.jetbrains.annotations.Nullable;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;

import java.util.Optional;
import java.util.function.BiPredicate;

public class ZoneArgumentFilterBuilder {

    private boolean ignoreGlobalPermission;
    private ZonePermission permission;
    private BiPredicate<Zone, CommandContext> predicate;

    public boolean shouldRunWithoutGlobalPermissionCheck() {
        return this.ignoreGlobalPermission;
    }

    public ZoneArgumentFilterBuilder setShouldRunWithoutGlobalPermissionCheck(boolean check) {
        this.ignoreGlobalPermission = check;
        return this;
    }

    public Optional<ZonePermission> getPermission() {
        return Optional.ofNullable(this.permission);
    }

    public ZoneArgumentFilterBuilder setPermission(@Nullable ZonePermission permission) {
        this.permission = permission;
        return this;
    }

    public BiPredicate<Zone, CommandContext> getFilter() {
        return this.predicate;
    }

    public ZoneArgumentFilterBuilder setFilter(BiPredicate<Zone, CommandContext> filter) {
        this.predicate = filter;
        return this;
    }

    public ZoneArgumentFilter build() {
        return new BasicZoneArgumentFilter(this);
    }
}
