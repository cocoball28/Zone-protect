package org.zone.region.group.key;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.plugin.PluginContainer;

/**
 * A key designed to be registered with a group which can be used to provide more information about this group, such as if this group can bypass a flag
 */
public interface GroupKey {

    /**
     * Gets the display name of the flag
     *
     * @return The display name of the flag
     */
    String getName();

    /**
     * Gets the id name of the flag
     *
     * @return The key version of the name of the flag
     */
    String getKey();

    /**
     * Gets the plugin that owns the group key
     *
     * @return The owning plugin
     */
    PluginContainer getPlugin();

    /**
     * Gets the key in is resource form
     *
     * @return The resource key of this group key
     */
    default ResourceKey getId() {
        return ResourceKey.builder().namespace(this.getPlugin()).value(this.getKey()).build();
    }

}
