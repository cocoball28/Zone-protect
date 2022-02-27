package org.zone.region.bounds.mode;

/**
 * All known {@link BoundMode} within the ZonePlugin
 */
public final class BoundModes {

    public static final ChunkBoundMode CHUNK = new ChunkBoundMode();
    public static final BlockBoundMode BLOCK = new BlockBoundMode();

    private BoundModes() {
        throw new RuntimeException("should not be constructed");
    }
}
