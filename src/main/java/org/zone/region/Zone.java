package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;
import org.zone.ZonePlugin;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.MembersFlag;
import org.zone.region.regions.Region;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class Zone implements Identifiable {

    private final @NotNull PluginContainer container;
    private final @NotNull Region region;
    private final @NotNull String key;
    private final @NotNull String name;
    private final @NotNull Collection<Flag> flags = new HashSet<>();
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

    public @NotNull Region getRegion() {
        return this.region;
    }

    public void save() throws IOException {
        
    }

    public <F extends Flag, T extends FlagType<F>> @NotNull Optional<F> getFlag(T type) {
        Optional<F> opFlag =
                this
                        .getFlags()
                        .parallelStream()
                        .filter(flag -> flag.getType().equals(type))
                        .map(flag -> (F) flag)
                        .findFirst();
        if (opFlag.isPresent()) {
            return opFlag;
        }
        return ZonePlugin.getZonesPlugin().getFlagManager().getDefaultFlags().loadDefault(type);
    }

    public MembersFlag getMembers() {
        return this.getFlag(FlagTypes.MEMBERS).orElseThrow(() -> new IllegalStateException("MembersFlag is missing " +
                "in zone: " + this.getId()));
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

    public boolean inRegion(@Nullable World<?, ?> world, @NotNull Vector3d vector3i) {
        return this.getRegion().inRegion(world, vector3i, this.getParent().isEmpty());
    }

    public boolean inRegion(@NotNull Location<?, ?> location) {
        return this.getRegion().inRegion(location, this.getParent().isEmpty());
    }

    public boolean inRegion(@NotNull Locatable locatable) {
        return this.getRegion().inRegion(locatable, this.getParent().isEmpty());
    }
}
