package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;

import java.util.*;
import java.util.stream.Collectors;

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

    public @NotNull Collection<Zone> getZone(@Nullable World<?, ?> world, @NotNull Vector3d worldPos) {
        return this.getZones().stream().filter(zone -> zone.inRegion(world, worldPos)).collect(Collectors.toSet());
    }

    public @NotNull Optional<Zone> getPriorityZone(@Nullable World<?, ?> world, @NotNull Vector3d worldPos) {
        Collection<Zone> zones = this.getZone(world, worldPos);
        if (zones.isEmpty()) {
            return Optional.empty();
        }
        for (Zone zone : zones) {
            if (zone.getParent().isPresent()) {
                zones.remove(zone.getParent().get());
            }
        }
        if (zones.size()==1) {
            return Optional.of(zones.iterator().next());
        }
        Collection<Zone> sortedZone = new TreeSet<>(Comparator.comparing(Identifiable::getId));
        sortedZone.addAll(zones);
        return Optional.of(sortedZone.iterator().next());
    }

    public void register(Zone zone) {
        this.zones.add(zone);
    }
}
