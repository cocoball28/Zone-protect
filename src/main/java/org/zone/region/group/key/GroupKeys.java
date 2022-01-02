package org.zone.region.group.key;

import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;

/**
 * All known group keys found within the zones plugin
 */
public enum GroupKeys implements GroupKey {
    OWNER("owner", "Owner"),
    HOME_OWNER("home_owner", "Home Owner"),
    BLOCK_BREAK("block_break", "Block Break"),
    BLOCK_PLACE("block_place", "Block Place"),
    INTERACT_DOOR("interact_door", "Interact with Door"),
    PLAYER_PREVENTION("prevent_players", "Prevent players from entering your zone"),
    INTERACT_ITEMFRAME("interact_itemframe", "Prevent players from interacting an itemframe in " +
            "your zone"),
    DAMAGE("damage", "Prevent damage from any other entity");
    private final String name;
    private final String key;

    GroupKeys(String key, String name) {
        this.name = name;
        this.key = key;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }
}
