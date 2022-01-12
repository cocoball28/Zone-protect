package org.zone;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.service.permission.Subject;

public enum Permissions {

    REGION_CREATE_BOUNDS("zone.region.create.bounds"),
    REGION_CREATE_CHUNK_BOUNDS("zone.region.create.chunk.bounds"),
    REGION_ADMIN_INFO("zone.region.admin.info"),
    BYPASS_INTERACTION_DOOR("zone.bypass.interaction.door"),
    BYPASS_INTERACTION_BLOCK("zone.bypass.interaction.block"),
    REGION_EDIT_BOUNDS("zone.region.edit.bounds"),
    BLOCK_BREAK_ENABLE("zone.region.flag.block.break.enable"),
    BLOCK_BREAK_DISABLE("zone.region.flag.block.break.disable"),
    BLOCK_BREAK_SET_GROUP("zone.region.flag.block.break.set.group"),
    BLOCK_BREAK_VIEW("zone.region.flag.block.break.view"),
    BLOCK_PLACE_ENABLE("zone.region.flag.block.place.enable"),
    BLOCK_PLACE_DISABLE("zone.region.flag.block.place.disable"),
    BLOCK_PLACE_SET_GROUP("zone.region.flag.block.place.set.group"),
    BLOCK_PLACE_VIEW("zone.region.flag.block.place.view"),
    INTERACT_DOOR_ENABLE("zone.region.flag.interact.door.enable"),
    INTERACT_DOOR_DISABLE("zone.region.flag.interact.door.disable"),
    INTERACT_DOOR_SET_GROUP("zone.region.flag.interact.door.set.group"),
    INTERACT_DOOR_VIEW("zone.region.flag.interact.door.view"),
    FLAG_SET("zone.region.flag.set"),
    FLAG_GET("zone.region.flag.get"),
    LEAVE_ZONE("zone.region.leave"),
    ZONE_SHOW_BOUNDS("zone.region.bounds.show"),
    MEMBER_GROUP_ADD("zone.region.flag.members.group.add"),
    MEMBER_GROUP_VIEW("zone.region.flag.members.group.view"),
    SET_GREETINGS("zone.region.flag.greetings.message.set"),
    REMOVE_GREETINGS("zone.region.flag.greetings.message.remove"),
    VIEW_GREETINGS("zone.region.flag.greetings.message.view"),
    SET_LEAVING_MESSAGE("zone.region.flag.leaving.message.set"),
    REMOVE_LEAVING_MESSAGE("zone.region.flag.leaving.message.remove"),
    VIEW_LEAVING_MESSAGE("zone.region.flag.leaving.message.view"),
    ENABLE_PLAYER_PREVENTION("zone.region.flag.player.prevention.enable"),
    DISABLE_PLAYER_PREVENTION("zone.region.flag.player.prevention.disable"),
    PLAYER_PREVENTION_GROUP_SET("zone.region.flag.player.prevention.set.group"),
    PLAYER_PREVENTION_VIEW("zone.region.flag.player.prevention.viee"),
    ENABLE_MONSTER_PREVENTION("zone.region.flag.monster.prevention.enable"),
    DISABLE_MONSTER_PREVENTION("zone.region.flag.monster.prevention.disable"),
    VIEW_MONSTER_PREVENTION("zone.region.flag.monster.prevention.view"),
    ENTITY_DAMAGE_PLAYER_ENABLE("zone.region.flag.entity.damage.player.enable"),
    ENTITY_DAMAGE_PLAYER_DISABLE("zone.region.flag.entity.damage.player.disable"),
    ENTITY_DAMAGE_PLAYER_GROUP_SET("zone.region.flag.entity.damage.player.set.group"),
    ENTITY_DAMAGE_PLAYER_VIEW("zone.region.flag.entity,damage.player.view"),
    PLAYER_FALL_DAMAGE_ENABLE("zone.region.flag.player.fall.damage.enable"),
    PLAYER_FALL_DAMAGE_DISABLE("zone.region.flag.player.fall.damage.disable"),
    PLAYER_FALL_DAMAGE_GROUP_SET("zone.region.flag.player.fall.damage.set.group"),
    PLAYER_FALL_DAMAGE_VIEW("zone.region.flag.player.fall.damage.view"),
    INTERACT_ITEMFRAME_ENABLE("zone.region.flag.interact.itemframe.enable"),
    INTERACT_ITEMFRAME_DISABLE("zone.region.flag.interact.itemframe.disable"),
    INTERACT_ITEMFRAME_SET_GROUP("zone.region.flag.interact.itemframe.set.group"),
    INTERACT_ITEMFRAME_VIEW("zone.region.flag.interact.itemframe.view"),
    ZONE_BALANCE_VIEW("zone.region.flag.eco.balance.view"),
    BYPASS_PLAYER_FALL_DAMAGE("zone.bypass.fall.damage.player"),
    BYPASS_ENTITY_DAMAGE_PLAYER("zone.bypass.entity.damage.player"),
    BYPASS_MONSTER_PREVENTION("zone.bypass.monster.prevention"),
    BYPASS_PLAYER_PREVENTION("zone.bypass.prevention.player"),
    BYPASS_DOOR_INTERACTION("zone.bypass.interaction.door"),
    BYPASS_ITEMFRAME_INTERACTION("zone.bypass.interaction.itemframe");

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
