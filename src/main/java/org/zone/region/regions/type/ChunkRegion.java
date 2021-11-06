package org.zone.region.regions.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.chunk.WorldChunk;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.regions.BoundedRegion;

public class ChunkRegion implements BoundedRegion {

    private final WorldChunk chunk;

    public ChunkRegion(WorldChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public Vector3i getMin() {
        return this.chunk.min();
    }

    @Override
    public Vector3i getMax() {
        return this.chunk.max();
    }

    @Override
    public World<?, ?> getWorld() {
        return this.chunk.world();
    }

    @Override
    public boolean inRegion(@Nullable World<?, ?> world, @NotNull Vector3i vector3i) {
        if (world!=null && !world.equals(this.getWorld())) {
            return false;
        }
        return this.chunk.contains(vector3i);
    }
}
