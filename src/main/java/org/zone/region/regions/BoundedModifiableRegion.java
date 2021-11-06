package org.zone.region.regions;

import org.spongepowered.math.vector.Vector3i;

public interface BoundedModifiableRegion extends BoundedRegion{

    void setMin(Vector3i vector3i);

    void setMax(Vector3i vector3i);

    default void setBounds(Vector3i min, Vector3i max) {
        this.setMax(max);
        this.setMin(min);
    }

    default void applyBounds(Vector3i vector1, Vector3i vector2) {
        int minX = Math.min(vector1.x(), vector2.x());
        int minY = Math.min(vector1.y(), vector2.y());
        int minZ = Math.min(vector1.z(), vector2.z());
        int maxX = Math.max(vector1.x(), vector2.x());
        int maxY = Math.max(vector1.x(), vector2.x());
        int maxZ = Math.max(vector1.x(), vector2.x());
        this.setBounds(new Vector3i(minX, minY, minZ), new Vector3i(maxX, maxY, maxZ));

    }
}
