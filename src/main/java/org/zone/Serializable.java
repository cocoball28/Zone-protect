package org.zone;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

import java.io.IOException;

public interface Serializable<F> {

    /**
     * Loads a value from the provided node
     *
     * @param node The node to load from
     *
     * @return The loaded value
     *
     * @throws IOException If failed to load
     */
    @NotNull F load(@NotNull ConfigurationNode node) throws IOException;

    /**
     * serializes the value to the node
     *
     * @param node The node to serialize to
     * @param save The value to save
     *
     * @throws IOException If failed to save
     */
    void save(@NotNull ConfigurationNode node, @Nullable F save) throws IOException;

    default void unsave(@NotNull ConfigurationNode node) throws IOException {
        this.save(node, null);
    }
}
