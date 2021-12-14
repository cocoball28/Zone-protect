package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;
import org.zone.ZonePlugin;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.eco.EcoFlag;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.regions.Region;

import java.util.*;

public class Zone implements Identifiable {

    private final @NotNull PluginContainer container;
    private final @NotNull Region region;
    private final @NotNull String key;
    private final @NotNull String name;
    private final @NotNull Collection<Flag> flags = new TreeSet<>(Comparator.comparing(flag -> flag.getType().getId()));
    private final @Nullable String parentId;

    public Zone(@NotNull ZoneBuilder builder) {
        this.parentId = builder.getParentId();
        this.flags.addAll(builder.getFlags());
        this.name = builder.getName();
        this.key = builder.getKey();
        this.region = builder.getRegion();
        this.container = builder.getContainer();
    }

    public @NotNull Optional<Zone> getParent() {
        return ZonePlugin.getZonesPlugin().getZoneManager().getZone(this.parentId);
    }

    public @NotNull Optional<String> getParentId() {
        return Optional.ofNullable(this.parentId);
    }

    public @NotNull Collection<Flag> getFlags() {
        return Collections.unmodifiableCollection(this.flags);
    }

    public boolean removeFlag(@NotNull Identifiable type) {
        Optional<Flag> opFlag = this.flags
                .parallelStream()
                .filter(flag -> type.getId().equals(flag.getType().getId()))
                .findFirst();

        if (opFlag.isEmpty()) {
            return false;
        }
        return this.flags.remove(opFlag.get());
    }

    public boolean addFlag(@NotNull Flag flag) {
        return this.flags.add(flag);
    }

    public boolean setFlag(@NotNull Flag flag) {
        this.removeFlag(flag.getType());
        return this.addFlag(flag);
    }

    public @NotNull Region getRegion() {
        return this.region;
    }

    public void save() throws ConfigurateException {
        ZonePlugin.getZonesPlugin().getZoneManager().save(this);
    }

    public <F extends Flag, T extends FlagType<F>> @NotNull Optional<F> getFlag(@NotNull T type) {
        Optional<F> opFlag = this
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
        return this
                .getFlag(FlagTypes.MEMBERS)
                .orElseThrow(() -> new IllegalStateException("MembersFlag is missing in zone: " + this.getId()));
    }

    public EcoFlag getEconomy() {
        return this
                .getFlag(FlagTypes.ECO)
                .orElseThrow(() -> new IllegalStateException("EcoFlag is missing in zone " + this.getId()));
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
