package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.zone.Identifiable;
import org.zone.region.Zone;
import org.zone.region.flag.meta.tag.TagsFlagType;

import java.io.IOException;
import java.util.Optional;

/**
 * A flag type is the specific type of a flag, each flag should have a unique FlagType which is
 * designed to serialize and deserialize the flag as well as provide generic metadata about the flag itself
 *
 * @param <F> The class type of the attached flag
 */
public interface FlagType<F extends Flag> extends Identifiable, Comparable<FlagType<?>> {

    interface TaggedFlagType<F extends Flag.TaggedFlag> extends FlagType<F> {

        F createCopyOfDefault();

        @Override
        @Deprecated
        default @NotNull F load(@NotNull ConfigurationNode node) throws IOException {
            throw new RuntimeException("Taggable flag cannot be loaded on its own");
        }

        @Override
        @Deprecated
        default void save(@NotNull ConfigurationNode node, @Nullable F save) throws IOException {
            throw new RuntimeException("Taggable flag cannot save on its own");
        }

        @Override
        @Deprecated
        default @NotNull Optional<F> createCopyOfDefaultFlag() {
            return Optional.of(this.createCopyOfDefault());
        }

        @Override
        default int compareTo(@NotNull FlagType<?> o) {
            if (o instanceof TagsFlagType) {
                return -1;
            }
            return FlagType.super.compareTo(o);
        }
    }

    /**
     * Loads a flag from the provided node
     *
     * @param node The node to load from
     *
     * @return The loaded flag
     *
     * @throws IOException If failed to load
     */
    @NotNull F load(@NotNull ConfigurationNode node) throws IOException;

    /**
     * serializes the flag to the node
     *
     * @param node The node to serialize to
     * @param save The flag to save
     *
     * @throws IOException If failed to save
     */
    void save(@NotNull ConfigurationNode node, @Nullable F save) throws IOException;

    /**
     * Checks if the provided zone can accept a instance of this flag
     *
     * @param zone the zone to compare
     *
     * @return if the zone can accept the flag
     */
    default boolean canApply(@NotNull Zone zone) {
        return true;
    }

    /**
     * Creates a copy of the defaults to this flag. This will be used if a flag cannot be found on a zone
     *
     * @return A copy of the defaults
     */
    @NotNull Optional<F> createCopyOfDefaultFlag();

    @Override
    default int compareTo(@NotNull FlagType<?> o) {
        return this.getId().compareTo(o.getId());
    }
}
