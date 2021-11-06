package org.zone.region.group;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.plugin.PluginContainer;

import java.util.Optional;

public class SimpleGroup implements Group {

    private final @NotNull String name;
    private final @NotNull String key;
    private final @NotNull PluginContainer plugin;
    private final @Nullable Group parent;

    public SimpleGroup(@NotNull PluginContainer plugin, @NotNull String key, @Nullable Group parent) {
        this(plugin, key, key, parent);
    }

    public SimpleGroup(@NotNull PluginContainer plugin, @NotNull String key, @NotNull String name, @Nullable Group parent) {
        this.key = key;
        this.plugin = plugin;
        this.name = name;
        this.parent = parent;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return this.plugin;
    }

    @Override
    public @NotNull String getKey() {
        return this.key;
    }

    @Override
    public Optional<Group> getParent() {
        return Optional.ofNullable(this.parent);
    }
}
