package org.zone.region.regions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface Region {

    boolean inRegion(@Nullable World<?, ?> world, @NotNull Vector3d vector3i, boolean ignoreY);

    default boolean inRegion(@NotNull Location<?, ?> location, boolean ignoreY) {
        return this.inRegion(location.world(), location.position(), ignoreY);
    }

    default boolean inRegion(@NotNull Locatable locatable, boolean ignoreY) {
        return this.inRegion(locatable.world(), locatable.serverLocation().position(), ignoreY);
    }

}
