package org.zone.region.bounds.mode;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

/**
 * Used to wrap the shift. Does not change the position
 *
 * @since 1.0.0
 */
public class BlockBoundMode implements BoundMode {
    @Override
    public @NotNull Location<? extends World<?, ?>, ?> shift(
            @NotNull Location<? extends World<?, ?>, ?> current, @NotNull Vector3i other) {
        return current;
    }

    @Override
    public @NotNull Location<? extends World<?, ?>, ?> shiftOther(
            @NotNull Location<? extends World<?, ?>, ?> other, @NotNull Vector3i current) {
        return other;
    }
}
