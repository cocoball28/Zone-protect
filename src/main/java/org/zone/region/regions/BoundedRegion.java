package org.zone.region.regions;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.math.vector.Vector3i;

public interface BoundedRegion extends Region {

    Vector3i getPointOne();

    Vector3i getPointTwo();

    ResourceKey getWorldKey();

    @Override
    default Vector3i getNearestPosition(Vector3i vector3i) {
        Vector3i min = this.getMin();
        Vector3i max = this.getMax();
        int x = vector3i.x();
        int y = vector3i.y();
        int z = vector3i.z();
        if (vector3i.x() < min.x()) {
            x = min.x();
        }
        if (vector3i.x() > max.x()) {
            x = max.x();
        }
        if (vector3i.y() < min.y()) {
            y = min.y();
        }
        if (vector3i.y() > max.y()) {
            y = max.y();
        }
        if (vector3i.z() < min.z()) {
            z = min.z();
        }
        if (vector3i.z() > max.z()) {
            z = max.z();
        }
        return new Vector3i(x, y, z);
    }

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
