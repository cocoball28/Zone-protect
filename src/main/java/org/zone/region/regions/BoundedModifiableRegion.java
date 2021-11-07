package org.zone.region.regions;

import org.spongepowered.math.vector.Vector3i;

public interface BoundedModifiableRegion extends BoundedRegion {

    void setPointOne(Vector3i vector3i);

    void setPointTwo(Vector3i vector3i);
}
