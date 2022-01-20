package org.zone.config;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.config.node.ZoneNode;

import java.io.File;
import java.util.Optional;

public class ZoneConfig {

    private final File file;
    private final HoconConfigurationLoader loader;
    private final CommentedConfigurationNode node;

    public ZoneConfig(@NotNull File file) {
        this.file = file;
        this.loader = HoconConfigurationLoader.builder().file(file).build();
        CommentedConfigurationNode node;
        try {
            node = this.loader.load();
        } catch (ConfigurateException e) {
            node = this.loader.createNode();
        }
        this.node = node;
    }

    public File getFile() {
        return this.file;
    }

    public HoconConfigurationLoader getLoader() {
        return this.loader;
    }

    public CommentedConfigurationNode getRoot() {
        return this.node;
    }

    public void save() throws ConfigurateException {
        this.loader.save(this.node);
    }

    public <T> Optional<T> get(ZoneNode<T> node) {
        return node.get(this);
    }

    public <T> T getOrElse(ZoneNode.WithDefault<T> node) {
        return node.getOrElse(this);
    }

    public <T> void set(ZoneNode<T> node, T value) throws SerializationException {
        node.set(this, value);
    }
}
