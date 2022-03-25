package org.zone.region.bounds.mode;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;
import org.zone.annotations.Typed;

/**
 * An interaction designed to shift the position of a creating/editing zone.
 * <p>
 * An example of this would be that if a chunk is required, a BoundMode which shifts to the
 * nearest chunk edge would be used
 *
 * @since 1.0.0
 */
@Typed(typesClass = BoundModes.class)
public interface BoundMode {

    /**
     * Shifts the location
     *
     * @param current The current location that should be shifted
     * @param other   The other point within the Zone (such as if the current is {@link org.zone.region.bounds.PositionType#ONE} then the other would be {@link org.zone.region.bounds.PositionType#TWO}
     *
     * @return The shifted position
     * @since 1.0.0
     */
    @NotNull Location<? extends World<?, ?>, ?> shift(
            @NotNull Location<? extends World<?, ?>, ?> current, @NotNull Vector3i other);

    @NotNull Location<? extends World<?, ?>, ?> shiftOther(
            @NotNull Location<? extends World<?, ?>, ?> other, @NotNull Vector3i current);
}
