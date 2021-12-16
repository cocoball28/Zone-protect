package org.zone.region.bounds;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Region {

    boolean contains(@Nullable World<?, ?> world, @NotNull Vector3d location, boolean ignoreY);

    Optional<Vector3i> getNearestPosition(@NotNull Vector3i vector3i);

    void save(ConfigurationNode node) throws SerializationException;

    Collection<Region> getChildren();

    default Collection<BoundedRegion> getTrueChildren() {
        Collection<Region> children = this.getChildren();
        Collection<BoundedRegion> trueChildren = children
                .parallelStream()
                .filter(region -> region instanceof BoundedRegion)
                .map(region -> (BoundedRegion) region)
                .collect(Collectors.toSet());
        Collection<BoundedRegion> childTrueChildren = children
                .parallelStream()
                .filter(region -> !(region instanceof BoundedRegion))
                .flatMap(region -> region.getTrueChildren().parallelStream())
                .collect(Collectors.toSet());
        trueChildren.addAll(childTrueChildren);
        return trueChildren;
    }

    default boolean contains(@NotNull Vector3d vector3d, boolean ignoreY) {
        return this.contains(null, vector3d, ignoreY);
    }

    default boolean contains(@NotNull Vector3i vector3i, boolean ignoreY) {
        return this.contains(null, vector3i.toDouble(), ignoreY);
    }

    default boolean contains(@NotNull Location<? extends World<?, ?>, ?> location, boolean ignoreY) {
        return this.contains(location.world(), location.position(), ignoreY);
    }


}
