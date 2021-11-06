package org.zone.region.regions;

import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

public interface BoundedRegion extends Region {

    Vector3i getMin();

    Vector3i getMax();

    World<?, ?> getWorld();
}
