package org.zone.permissions;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;
import org.zone.annotations.Typed;

import java.util.Optional;

@Typed(typesClass = ZonePermissions.class)
public interface ZonePermission {

    @NotNull String[] getPermissionNode();

    boolean isDefaultAllowed();

    default boolean hasPermission(@NotNull Subject subject) {
        if (subject.hasPermission(this.getPermission())) {
            return true;
        }
        Optional<PermissionService> opService = Sponge
                .serviceProvider()
                .provide(PermissionService.class);
        if (opService.isPresent()) {
            return false;
        }
        return this.isDefaultAllowed();
    }

    default @NotNull String getPermission() {
        return String.join(".", this.getPermissionNode());
    }
}
