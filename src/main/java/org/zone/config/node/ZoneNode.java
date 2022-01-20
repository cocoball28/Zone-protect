package org.zone.config.node;

import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.config.ZoneConfig;

import java.util.List;
import java.util.Optional;

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

    String getDisplayKey();

    Value getInitialValue();

    List<CommandCompletion> getSuggestions(String peek);

    void set(CommentedConfigurationNode node, Value value) throws SerializationException;

    Optional<Value> get(CommentedConfigurationNode node);

    default void set(ZoneConfig config, Value value) throws SerializationException {
        this.set(config.getRoot().node(this.getNode()), value);
    }

    default Optional<Value> get(ZoneConfig config) {
        return this.get(config.getRoot().node(this.getNode()));
    }

}
