package org.zone.memory;

import org.zone.region.ZoneBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Designed to hold temporary data
 */
public class MemoryHolder {

    private final Map<UUID, ZoneBuilder> zoneBuilders = new HashMap<>();

    /**
     * Gets the ZoneBuilder that is being currently build. A example of this would be when a bounds start has occurred but not the end
     *
     * @param uuid The player's UUID
     *
     * @return A Optional of the ZoneBuilder
     */
    public Optional<ZoneBuilder> getZoneBuilder(UUID uuid) {
        return this.zoneBuilders
                .entrySet()
                .parallelStream()
                .filter(entry -> entry.getKey().equals(uuid))
                .findAny()
                .map(Map.Entry::getValue);
    }

    /**
     * Registers a zonebuilder to the temporary space
     *
     * @param uuid    The players UUID
     * @param builder the builder to register
     */
    public void registerZoneBuilder(UUID uuid, ZoneBuilder builder) {
        if (this.zoneBuilders.containsKey(uuid)) {
            this.zoneBuilders.replace(uuid, builder);
            return;
        }
        this.zoneBuilders.put(uuid, builder);
    }

    /**
     * Unregisters a zonebuilder from the temporary space
     *
     * @param uuid The players UUID
     */
    public void unregisterZoneBuilder(UUID uuid) {
        this.zoneBuilders.remove(uuid);
    }

}
