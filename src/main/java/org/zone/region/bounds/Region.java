package org.zone.region.bounds;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.Zone;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The region of the zone
 * The locations that the region covers
 *
 * @since 1.0.0
 */
public interface Region {

    /**
     * Checks if the position in the world is contained within this region
     *
     * @param location The block position to compare
     * @param ignoreY  true will ignore the height in the provided location
     *
     * @return True if the region contains that block position
     * @since 1.0.0
     */
    boolean contains(@NotNull Vector3d location, boolean ignoreY);

    Collection<? extends Entity> getEntities(@NotNull World<?, ?> world);

    /**
     * Gets the location within the region that is nearest to the provided block position
     *
     * @param vector3i The block position to compare
     *
     * @return The closes block position, if {@link Optional#empty()} then the closes could not be found. This is highly unlikely but is possible
     * @since 1.0.0
     */
    Optional<Vector3i> getNearestPosition(@NotNull Vector3i vector3i);

    /**
     * Gets the location within the region that is nearest to the provided block position,
     * this version of the method ignores the y position
     *
     * @param vector the block position to compare ignoring the y
     *
     * @return The closes block to the provided position (without y), if {@link Optional#empty()}
     *         then the closes could not be found
     * @since 1.0.0
     */
    Optional<Vector2i> getNearestPosition(Vector2i vector);

    /**
     * Serializes the region to the provided node
     *
     * @param node The node to serialize this region to
     *
     * @throws SerializationException If the zone could not be serialized
     * @since 1.0.0
     */
    void save(@NotNull ConfigurationNode node) throws SerializationException;

    /**
     * Gets the children of this region
     *
     * @return The children of this region, the collection should be unmodifiable and can be any type of Collection
     * @since 1.0.0
     */
    Collection<Region> getChildren();

    /**
     * Gets all the {@link BoundedRegion} found within this region. This is mainly for {@link ChildRegion}
     *
     * @return All the {@link BoundedRegion} within this region
     * @since 1.0.0
     */
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

    /**
     * Checks if the provided position is contained within the region
     *
     * @param vector3i The block position to compare
     * @param ignoreY  if true, the height will be ignored in the check
     *
     * @return If true, the position is contained
     * @since 1.0.0
     */
    default boolean contains(@NotNull Vector3i vector3i, boolean ignoreY) {
        return this.contains(vector3i.toDouble(), ignoreY);
    }

    /**
     * Checks if the provided position is contained within the region. Note that the world is not compared, use {@link Zone#inRegion(Location)} for the world to also be compared
     *
     * @param location The block position to be compared
     * @param ignoreY  if true, the height will be ignored in the chunk
     *
     * @return If true, the position is contained
     * @since 1.0.0
     */
    default boolean contains(
            @NotNull Location<? extends World<?, ?>, ?> location, boolean ignoreY) {
        return this.contains(location.position(), ignoreY);
    }

    /**
     * Gets the location within the region that is nearest to the provided block position,
     * this version of the method ignores the y position
     *
     * @param vector the block position to compare ignoring the y
     *
     * @return The closes block to the provided position (maining the y position), if
     *         {@link Optional#empty()} then the closes could not be found
     * @since 1.0.0
     */
    default Optional<Vector3i> getNearestPosition(@NotNull Vector3i vector, boolean ignoreHeight) {
        if (ignoreHeight) {
            return this
                    .getNearestPosition(new Vector2i(vector.x(), vector.z()))
                    .map(vector2i -> new Vector3i(vector2i.x(), vector.y(), vector2i.y()));
        }
        return this.getNearestPosition(vector);
    }
}
