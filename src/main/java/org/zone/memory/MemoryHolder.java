package org.zone.memory;

import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;
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
    private final Map<UUID, BossBar> zoneBuilderBossBars = new HashMap<>();


    public Optional<BossBar> getZoneBuilderBossBar(@NotNull UUID uuid) {
        return this.zoneBuilderBossBars
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(uuid))
                .map(Map.Entry::getValue)
                .findAny();
    }

    public void registerZoneBuilderBossBar(@NotNull UUID uuid, @NotNull BossBar bossBar) {
        if (this.zoneBuilderBossBars.containsKey(uuid)) {
            this.zoneBuilderBossBars.replace(uuid, bossBar);
            return;
        }
        this.zoneBuilderBossBars.put(uuid, bossBar);
    }

    public void unregisterZoneBuilderBossBar(@NotNull UUID uuid) {
        this.zoneBuilderBossBars.remove(uuid);
    }

    /**
     * Gets the ZoneBuilder that is being currently build. A example of this would be when a bounds start has occurred but not the end
     *
     * @param uuid The player's UUID
     *
     * @return A Optional of the ZoneBuilder
     */
    public Optional<ZoneBuilder> getZoneBuilder(@NotNull UUID uuid) {
        return this.zoneBuilders
                .entrySet()
                .parallelStream()
                .filter(entry -> entry.getKey().equals(uuid))
                .findAny()
                .map(Map.Entry::getValue);
    }

    /**
     * Registers a zone builder to the temporary space
     *
     * @param uuid    The players UUID
     * @param builder the builder to register
     */
    public void registerZoneBuilder(@NotNull UUID uuid, @NotNull ZoneBuilder builder) {
        if (this.zoneBuilders.containsKey(uuid)) {
            this.zoneBuilders.replace(uuid, builder);
            return;
        }
        this.zoneBuilders.put(uuid, builder);
    }

    /**
     * Unregisters a zone builder from the temporary space
     *
     * @param uuid The players UUID
     */
    public void unregisterZoneBuilder(@NotNull UUID uuid) {
        this.zoneBuilders.remove(uuid);
    }

}
