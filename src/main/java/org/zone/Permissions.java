package org.zone;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.service.permission.Subject;

public enum Permissions {

    REGION_CREATE_BOUNDS("zone.region.create.bounds"),
    REGION_ADMIN_INFO("zone.region.admin.info"),
    BYPASS_DOOR_INTERACTION("zone.bypass.interaction.door");

    private final @NotNull String permission;

    Permissions(@NotNull String permission) {
        this.permission = permission;
    }

    public @NotNull String getPermission() {
        return this.permission;
    }

    public boolean hasPermissions(@NotNull Subject player) {
        return player.hasPermission(this.permission);
    }

}
