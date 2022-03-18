package org.zone;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;

public interface Identifiable {

    /**
     * Gets the name of the {@link Identifiable}
     *
     * @return The name of the {@link Identifiable}
     */
    @NotNull String getName();

    /**
     * Gets the plugin container of a plugin. Here, {@link ZonePlugin#getPluginContainer()}
     *
     * @return The plugin container of a plugin.
     */
    @NotNull PluginContainer getPlugin();

    /**
     * Gets the key of the {@link Identifiable}
     *
     * A key is just the name of the {@link Identifiable} in lower case
     *
     * @return The key of the {@link Identifiable}
     */
    @NotNull String getKey();

    /**
     * A default method which gets the id of the {@link Identifiable}
     *
     * An id is just the Plugin id + the key
     *
     * @return The id of the {@link Identifiable}
     */
    default @NotNull String getId() {
        return this.getPlugin().metadata().id() + ":" + this.getKey();
    }


}
