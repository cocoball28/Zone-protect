package org.zone.region.group.key;

import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;

public enum GroupKeys implements GroupKey {
    OWNER("owner", "Owner"),
    HOME_OWNER("home_owner", "Home Owner"),
    BLOCK_BREAK("block_break", "Block Break"),
    INTERACT_DOOR("interact_door", "Interact with Door");

    private final String name;
    private final String key;

    private GroupKeys(String key, String name){
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
