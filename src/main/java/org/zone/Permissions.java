package org.zone;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.service.permission.Subject;

public enum Permissions {

    REGION_CREATE_BOUNDS("zone.region.create.bounds"),
    REGION_ADMIN_INFO("zone.region.admin.info"),
    BYPASS_INTERACTION_DOOR("zone.bypass.interaction.door"),
    BYPASS_INTERACTION_BLOCK("zone.bypass.interaction.block"),
    REGION_EDIT_BOUNDS("zone.region.edit.bounds"),
    FLAG_SET("zone.region.flag.set"),
    FLAG_GET("zone.region.flag.get"),
    SET_GREETINGS("zone.region.flag.greetings.message.set"),
    REMOVE_GREETINGS("zone.region.flag.greetings.message.remove"),
    SET_LEAVING_MESSAGE("zone.region.flag.leaving.message.set"),
    REMOVE_LEAVING_MESSAGE("zone.region.flag.leaving.message.remove"),
    BYPASS_PLAYER_FALL_DAMAGE("zone.override.fall.damage.player"),
    BYPASS_PLAYER_ENTITY_DAMAGE("zone.override.entity.damage.player"),
    BYPASS_MONSTER_PREVENTION("zone.override.monster.prevention"),
    BYPASS_GREETINGS("zone.override.greetings"),
    BYPASS_LEAVING("zone.override.leaving"),
    BYPASS_PLAYER_PREVENTION("zone.bypass.prevention.player"),
    BYPASS_DOOR_INTERACTION("zone.override.interaction.door"),
    BYPASS_ITEMFRAME_INTERACTION("zone.override.interaction.itemframe");

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
