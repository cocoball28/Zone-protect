package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.group.Group;
import org.zone.region.regions.Region;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class Zone implements Identifiable {

    private final @NotNull PluginContainer container;
    private final @NotNull Region region;
    private final @NotNull String key;
    private final @NotNull String name;
    private final @NotNull Collection<Flag> flags = new HashSet<>();
    private final @NotNull Collection<Group> groups = new HashSet<>();
    private final @Nullable Zone parent;

    @Deprecated
    public Zone(@Nullable Zone parent, @NotNull Region region, @NotNull PluginContainer pluginContainer,
                @NotNull String key,
                @NotNull String name) {
        this.container = pluginContainer;
        this.region = region;
        this.key = key;
        this.name = name;
        this.parent = parent;
    }

    public Zone(@NotNull ZoneBuilder builder) {
        this.parent = builder.getParent();
        this.groups.addAll(builder.getGroups());
        this.flags.addAll(builder.getFlags());
        this.name = builder.getName();
        this.key = builder.getKey();
        this.region = builder.getRegion();
        this.container = builder.getContainer();
    }

    public @NotNull Optional<Zone> getParent() {
        return Optional.ofNullable(this.parent);
    }

    public @NotNull Collection<Flag> getFlags() {
        return this.flags;
    }

    public @NotNull Collection<Group> getGroups() {
        return this.groups;
    }

    public @NotNull Region getRegion() {
        return this.region;
    }

    public <F extends Flag, T extends FlagType<F>> @NotNull Optional<F> getFlag(Class<T> fClass) {
        return this
                .getFlags()
                .parallelStream()
                .filter(flag -> fClass.isInstance(flag.getType()))
                .map(flag -> (F) flag)
                .findFirst();
    }


    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return this.container;
    }

    @Override
    public @NotNull String getKey() {
        return this.key;
    }
}
