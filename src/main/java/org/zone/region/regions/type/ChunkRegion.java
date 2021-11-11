package org.zone.region.regions.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.chunk.WorldChunk;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.regions.BoundedRegion;

import java.util.Optional;

public class ChunkRegion implements BoundedRegion {

    private final int chunkX;
    private final @Nullable Integer chunkY;
    private final int chunkZ;
    private final @NotNull ResourceKey world;
    private ServerWorld cachedWorld;

    public ChunkRegion(@NotNull WorldChunk chunk, boolean ignoreY) {
        this(((ServerWorld) chunk.world()).key(), chunk.chunkPosition().x(), ignoreY ? null:chunk.chunkPosition().y(),
                chunk.chunkPosition().z());
    }

    public ChunkRegion(@NotNull ResourceKey world, int chunkX, @Nullable Integer chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
        this.world = world;
        this.cache();
    }

    public Optional<ServerWorld> getCachedWorld() {
        return Optional.ofNullable(this.cachedWorld);
    }

    private void cache() {
        Sponge.server().worldManager().loadWorld(this.world).thenAccept(world ->
                this.cachedWorld = world
        );
    }

    @Override
    public Vector3i getMin() {
        int y = this.chunkY==null ? 0:this.chunkY;
        return this
                .cachedWorld
                .loadChunk(this.chunkX, y, this.chunkZ, true)
                .orElseThrow(() -> new RuntimeException("Could not load bottom chunk"))
                .min();
    }

    @Override
    public Vector3i getMax() {
        int y = this.chunkY==null ? 16:this.chunkY;
        return this
                .cachedWorld
                .loadChunk(this.chunkX, y, this.chunkZ, true)
                .orElseThrow(() -> new RuntimeException("Could not load top chunk"))
                .max();
    }

    @Override
    public Vector3i getPointOne() {
        return this.getMin();
    }

    @Override
    public Vector3i getPointTwo() {
        return this.getMax();
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
        if (world!=null && !((ServerWorld) world).key().equals(this.getWorldKey())) {
            return false;
        }
        if (this.chunkY==null || ignoreY) {
            int chunkX = this.chunkX >> 4;
            int chunkZ = this.chunkZ >> 4;
            return (chunkX==this.chunkX) && (chunkZ==this.chunkZ);
        }

        return this
                .cachedWorld
                .loadChunk(this.chunkX, this.chunkY, this.chunkZ, true)
                .orElseThrow(() -> new RuntimeException("Could not load top chunk"))
                .contains(vector3d.toInt());
    }
}
