package org.zone.config.node;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.annotations.Typed;
import org.zone.config.ZoneConfig;
import org.zone.config.command.ConfigCommandNode;

import java.util.Collection;
import java.util.Optional;

/**
 * A node within the Zone config
 * @param <Value>
 * @since 1.0.1
 */
@Typed(typesClass = ZoneNodes.class)
public interface ZoneNode<Value> {

    interface WithDefault<Value> extends ZoneNode<Value> {

        @NotNull Value getDefault();

        default @NotNull Value getOrElse(@NotNull ZoneConfig config) {
            return this.get(config).orElse(this.getDefault());
        }

        default @NotNull Value getOrElse(@NotNull CommentedConfigurationNode node) {
            return this.get(node).orElse(this.getDefault());
        }

    }

    @NotNull Object[] getNode();

    @NotNull Value getInitialValue();

    @NotNull Collection<ConfigCommandNode<?>> getNodes();

    void set(@NotNull CommentedConfigurationNode node, @NotNull Value value) throws
            SerializationException;

    @NotNull Optional<Value> get(@NotNull CommentedConfigurationNode node);

    default void set(@NotNull ZoneConfig config, @NotNull Value value) throws
            SerializationException {
        this.set(config.getRoot().node(this.getNode()), value);
    }

    default @NotNull Optional<Value> get(ZoneConfig config) {
        return this.get(config.getRoot().node(this.getNode()));
    }

}
