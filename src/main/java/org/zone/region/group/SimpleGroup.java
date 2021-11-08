package org.zone.region.group;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SimpleGroup implements Group {

    private final @NotNull String name;
    private final @NotNull String key;
    private final @NotNull PluginContainer plugin;
    private @Nullable Group parent;
    private final boolean canBeRemoved;

    public static final Group VISITOR = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(), "vistor", "visitor",
            null, false);
    public static final Group HOME_OWNER = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(), "home_owner",
            "home owner", VISITOR,
            false);
    public static final Group OWNER = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(), "owner", "owner",
            HOME_OWNER, false);

    public SimpleGroup(@NotNull PluginContainer plugin, @NotNull String key, @NotNull Group parent) {
        this(plugin, key, key, parent);
    }

    public SimpleGroup(@NotNull PluginContainer plugin, @NotNull String key, @NotNull String name, @SuppressWarnings("NullableProblems") @NotNull Group parent) {
        this(plugin, key, name, parent, true);
    }

    private SimpleGroup(@NotNull PluginContainer plugin, @NotNull String key, @NotNull String name,
                        @Nullable Group parent, boolean canRemove) {
        this.key = key;
        this.plugin = plugin;
        this.name = name;
        this.parent = parent;
        this.canBeRemoved = canRemove;
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

    @Override
    public void setParent(@NotNull Group group) {
        this.parent = group;
    }

    @Override
    public boolean canBeRemoved() {
        return this.canBeRemoved;
    }

    public static Collection<Group> createDefaultGroup() {
        return Arrays.asList(VISITOR, HOME_OWNER, OWNER);
    }
}
