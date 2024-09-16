package org.zone.region.group.key;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;

/**
 * All known group keys found within the zones plugin
 *
 * @since 1.0.1
 */
public enum GroupKeys implements GroupKey {

    OWNER("owner", "Owner"),
    NEW_MEMBER("new_member", "New Member"),
    HOME_OWNER("home_owner", "Home Owner"),
    BLOCK_BREAK("block_break", "Block Break"),
    BLOCK_PLACE("block_place", "Block Place"),
    INTERACT_DOOR("interact_door", "Interact with Door"),
    PLAYER_PREVENTION("prevent_players", "Prevent players from entering your zone"),
    INTERACT_ITEMFRAME("interact_itemframe",
            "Prevent players from interacting an itemframe in " + "your zone"),
    ENTITY_DAMAGE_PLAYER("entity_damage_player", "Prevent damage from any other entity"),
    PLAYER_FALL_DAMAGE("player_fall_damage", "Prevent player  fall damage"),
    PLAYER_FIRE_DAMAGE("player_fire_damage", "Prevent fire damage to players"),
    CREATE_SHOP("create_shop", "Create a shop"),
    BUY_FROM_SHOP("buy_from_shop", "Buy from shop");

    private final @NotNull String name;
    private final @NotNull String key;

    GroupKeys(@NotNull String key, @NotNull String name) {
        this.name = name;
        this.key = key;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return this.key;
    }
}
