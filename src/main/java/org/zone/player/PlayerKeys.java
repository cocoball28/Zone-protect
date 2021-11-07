package org.zone.player;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.zone.ZonePlugin;
import org.zone.region.ZoneBuilder;

public class PlayerKeys {

    public static final Key<Value<ZoneBuilder>> REGION_BUILDER =
            Key
                    .builder()
                    .elementType(ZoneBuilder.class)
                    .key(ResourceKey
                            .builder()
                            .namespace(ZonePlugin.getInstance().getContainer())
                            .value("region_builder")
                            .build())
                    .build();


}
