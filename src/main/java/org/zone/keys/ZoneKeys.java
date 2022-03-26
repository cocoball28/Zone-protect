package org.zone.keys;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.zone.ZonePlugin;

public final class ZoneKeys {

    public static final Key<Value<String>> HUMAN_AI_ATTACHED_ZONE_ID = Key.from(ZonePlugin
            .getZonesPlugin()
            .getPluginContainer(), "human_ai_attached_zone_id", String.class);

    private ZoneKeys() {
        throw new RuntimeException("Couldn't construct class");
    }


}
