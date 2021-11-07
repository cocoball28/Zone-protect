package org.zone.region.regions.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.regions.BoundedModifiableRegion;

public class PointRegion implements BoundedModifiableRegion {

    private @NotNull Vector3i pointOne;
    private @NotNull Vector3i pointTwo;
    private final @NotNull World<?, ?> world;

    public PointRegion(@NotNull World<?, ?> world, @NotNull Vector3i pointOne, @NotNull Vector3i pointTwo) {
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
        this.world = world;
    }

    @Override
    public void setPointOne(@NotNull Vector3i vector3i) {
        this.pointOne = vector3i;
    }

    @Override
    public void setPointTwo(@NotNull Vector3i vector3i) {
        this.pointTwo = vector3i;
    }

    @Override
    public Vector3i getPointOne() {
        return this.pointOne;
    }

    @Override
    public Vector3i getPointTwo() {
        return this.pointTwo;
    }

    @Override
    public @NotNull World<?, ?> getWorld() {
        return this.world;
    }

    @Override
    public boolean inRegion(@Nullable World<?, ?> world, @NotNull Vector3i vector3i) {
        if (world!=null && !world.equals(this.world)) {
            return false;
        }
        if (!(this.pointOne.x() <= vector3i.x() && this.pointTwo.x() >= vector3i.x())) {
            return false;
        }
        if (!(this.pointOne.y() <= vector3i.y() && this.pointTwo.y() >= vector3i.y())) {
            return false;
        }
        return this.pointOne.z() <= vector3i.z() && this.pointTwo.z() >= vector3i.z();
    }
}
