package org.zone.memory;

import org.zone.region.ZoneBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MemoryHolder {

    private Map<UUID, ZoneBuilder> zoneBuilders = new HashMap<>();


    public Optional<ZoneBuilder> getZoneBuilder(UUID uuid) {
        return this.zoneBuilders.entrySet().parallelStream().filter(entry -> entry.getKey().equals(uuid)).findAny().map(Map.Entry::getValue);
    }

    public void registerZoneBuilder(UUID uuid, ZoneBuilder builder) {
        if (this.zoneBuilders.containsKey(uuid)) {
            this.zoneBuilders.replace(uuid, builder);
            return;
        }
        this.zoneBuilders.put(uuid, builder);
    }

    public void unregisterZoneBuilder(UUID uuid) {
        this.zoneBuilders.remove(uuid);
    }

}
