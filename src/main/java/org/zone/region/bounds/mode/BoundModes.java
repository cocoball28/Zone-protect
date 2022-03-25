package org.zone.region.bounds.mode;

/**
 * All known {@link BoundMode} within the ZonePlugin
 *
 * @since 1.0.0
 */
public final class BoundModes {

    public static final ChunkBoundMode CHUNK = new ChunkBoundMode();
    public static final BlockBoundMode BLOCK = new BlockBoundMode();

    private BoundModes() {
        throw new RuntimeException("Should not be constructed");
    }
}
