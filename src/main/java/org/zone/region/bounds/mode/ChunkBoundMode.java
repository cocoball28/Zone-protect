package org.zone.region.bounds.mode;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.chunk.WorldChunk;
import org.spongepowered.math.vector.Vector3i;

/**
 * Shifts the position to the closest edge of a chunk
 */
public class ChunkBoundMode implements BoundMode {
    @Override
    public @NotNull Location<? extends World<?, ?>, ?> shift(@NotNull Location<? extends World<?, ?>, ?> current, @NotNull Vector3i other) {
        Vector3i chunkPos = current.chunkPosition();
        WorldChunk chunk = current.world().chunk(chunkPos);
        Vector3i min = chunk.min();
        Vector3i max = chunk.max();
        int minDistance = current.blockPosition().distanceSquared(min);
        int maxDistance = current.blockPosition().distanceSquared(max);

        if (minDistance > maxDistance) {
            return current.world().location(min);
        }
        return current.world().location(max);
    }
}
