package org.zone;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;

public interface Identifiable {

    @NotNull String getName();

    @NotNull PluginContainer getPlugin();

    @NotNull String getKey();

    default @NotNull String getId() {
        return this.getPlugin().metadata().id() + ":" + this.getKey();
    }


}
