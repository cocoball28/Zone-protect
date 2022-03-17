package org.zone.config.node;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.annotations.Typed;
import org.zone.config.ZoneConfig;
import org.zone.config.command.ConfigCommandNode;

import java.util.Collection;
import java.util.Optional;

@Typed(typesClass = ZoneNodes.class)
public interface ZoneNode<Value> {

    interface WithDefault<Value> extends ZoneNode<Value> {

        Value getDefault();

        default Value getOrElse(ZoneConfig config) {
            return this.get(config).orElse(this.getDefault());
        }

        default Value getOrElse(CommentedConfigurationNode node) {
            return this.get(node).orElse(this.getDefault());
        }

    }

    Object[] getNode();

    Value getInitialValue();

    Collection<ConfigCommandNode<?>> getNodes();

    void set(CommentedConfigurationNode node, Value value) throws SerializationException;

    Optional<Value> get(CommentedConfigurationNode node);

    default void set(ZoneConfig config, Value value) throws SerializationException {
        this.set(config.getRoot().node(this.getNode()), value);
    }

    default Optional<Value> get(ZoneConfig config) {
        return this.get(config.getRoot().node(this.getNode()));
    }

}
