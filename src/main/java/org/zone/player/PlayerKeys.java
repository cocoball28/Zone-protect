package org.zone.player;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.zone.ZonePlugin;
import org.zone.region.regions.Region;

@SuppressWarnings("HardCodedStringLiteral")
public class PlayerKeys {

    Key<Value<Region>> REGION_BUILDER =
            Key
                    .builder()
                    .elementType(Region.class)
                    .key(ResourceKey
                            .builder()
                            .namespace(ZonePlugin.getInstance().getContainer())
                            .value("region_builder")
                            .build())
                    .build();


}
