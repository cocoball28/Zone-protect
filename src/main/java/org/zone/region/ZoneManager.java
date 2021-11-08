package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;

import java.util.*;

public class ZoneManager {

    private final @NotNull Collection<Zone> zones = new TreeSet<>(Comparator.comparing(Identifiable::getId));

    public @NotNull Collection<Zone> getZones() {
        return Collections.unmodifiableCollection(this.zones);
    }

    public @NotNull Optional<Zone> getZone(PluginContainer container, String key) {
        return this.getZone(container.metadata().id() + ":" + key);
    }

    public @NotNull Optional<Zone> getZone(String id) {
        return this.getZones().parallelStream().filter(zone -> zone.getId().equals(id)).findAny();
    }

    public void register(Zone zone) {
        this.zones.add(zone);
    }
}
