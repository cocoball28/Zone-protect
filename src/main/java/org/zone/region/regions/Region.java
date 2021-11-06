package org.zone.region.regions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

public interface Region {

    boolean inRegion(@Nullable World<?, ?> world, @NotNull Vector3i vector3i);

    default boolean inRegion(@NotNull Location<?, ?> location) {
        return this.inRegion(location.world(), location.blockPosition());
    }

    default boolean inRegion(@NotNull Locatable locatable) {
        return this.inRegion(locatable.world(), locatable.blockPosition());
    }

}
