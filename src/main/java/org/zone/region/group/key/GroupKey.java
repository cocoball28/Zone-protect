package org.zone.region.group.key;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.plugin.PluginContainer;

public interface GroupKey {

    String getName();

    String getKey();

    PluginContainer getPlugin();

    default ResourceKey getId() {
        return ResourceKey
                .builder()
                .namespace(this.getPlugin())
                .value(this.getKey())
                .build();
    }

}
