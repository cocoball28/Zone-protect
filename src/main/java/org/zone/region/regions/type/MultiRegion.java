package org.zone.region.regions.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.regions.Region;

import java.util.Collection;
import java.util.HashSet;

public class MultiRegion implements Region {

    private final Collection<Region> regions = new HashSet<>();

    public Collection<Region> getRegions() {
        return this.regions;
    }

    @Override
    public boolean inRegion(@Nullable World<?, ?> world, @NotNull Vector3i vector3i) {
        return this.regions.stream().anyMatch(region -> region.inRegion(world, vector3i));
    }
}
