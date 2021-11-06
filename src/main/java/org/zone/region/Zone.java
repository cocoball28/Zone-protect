package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
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

    public Zone(@NotNull Region region, @NotNull PluginContainer pluginContainer, @NotNull String key, @NotNull String name) {
        this.container = pluginContainer;
        this.region = region;
        this.key = key;
        this.name = name;
    }

    public @NotNull Collection<Flag> getFlags() {
        return this.flags;
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
