package org.zone.region.regions;

import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

public interface BoundedRegion extends Region {

    Vector3i getPointOne();

    Vector3i getPointTwo();

    World<?, ?> getWorld();

    default Vector3i getMin() {
        int minX = Math.min(this.getPointOne().x(), this.getPointTwo().x());
        int minY = Math.min(this.getPointOne().y(), this.getPointTwo().y());
        int minZ = Math.min(this.getPointOne().z(), this.getPointTwo().z());
        return new Vector3i(minX, minY, minZ);
    }

    default Vector3i getMax() {
        int maxX = Math.max(this.getPointOne().x(), this.getPointTwo().x());
        int maxY = Math.max(this.getPointOne().y(), this.getPointTwo().y());
        int maxZ = Math.max(this.getPointOne().z(), this.getPointTwo().z());
        return new Vector3i(maxX, maxY, maxZ);

    }
}
