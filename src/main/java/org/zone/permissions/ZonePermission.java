package org.zone.permissions;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;

import java.util.Optional;

public interface ZonePermission {

    String[] getPermissionNode();

    boolean isDefaultAllowed();

    default boolean hasPermission(Subject subject) {
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

    default String getPermission() {
        return String.join(".", this.getPermissionNode());
    }
}
