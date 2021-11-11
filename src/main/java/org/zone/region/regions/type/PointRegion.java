package org.zone.region.regions.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.regions.BoundedModifiableRegion;

public class PointRegion implements BoundedModifiableRegion {

    private @NotNull Vector3i pointOne;
    private @NotNull Vector3i pointTwo;
    private final @NotNull ResourceKey world;

    public PointRegion(@NotNull ResourceKey world, @NotNull Vector3i pointOne, @NotNull Vector3i pointTwo) {
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
    public @NotNull Vector3i getPointOne() {
        return this.pointOne;
    }

    @Override
    public @NotNull Vector3i getPointTwo() {
        return this.pointTwo;
    }

    @Override
    public @NotNull ResourceKey getWorldKey() {
        return this.world;
    }

    @Override
    public boolean inRegion(@Nullable World<?, ?> world, @NotNull Vector3d vector3d, boolean ignoreY) {
        if (world!=null && (!(world instanceof ServerWorld))) {
            return false;
        }
        if (world!=null && !((ServerWorld) world).key().equals(this.world)) {
            return false;
        }
        Vector3i max = this.getMax();
        Vector3i min = this.getMin();
        if (!(min.x() <= vector3d.x() && max.x() >= vector3d.x())) {
            return false;
        }
        if (!ignoreY && !(min.y() <= vector3d.y() && max.y() >= vector3d.y())) {
            return false;
        }
        return min.z() <= vector3d.z() && max.z() >= vector3d.z();
    }
}
