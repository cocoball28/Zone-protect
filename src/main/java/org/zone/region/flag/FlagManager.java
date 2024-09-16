package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.zone.Identifiable;
import org.zone.IdentifiableManager;
import org.zone.ZonePlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The manager of all flags, this is where you should register your custom flag or get all flags.
 * <p>
 * Use {@link ZonePlugin#getFlagManager()} to get an instance of this flag manager
 * @since 1.0.0
 */
public class FlagManager implements IdentifiableManager.Typed<FlagType<?>> {

    private final Collection<FlagType<?>> flags = new TreeSet<>(Comparator.comparing(Identifiable::getId));
    private final DefaultFlagFile defaultFlags = new DefaultFlagFile();

    public FlagManager() {
        this.flags.addAll(ZonePlugin
                .getZonesPlugin()
                .getVanillaTypes(FlagType.class)
                .map(type -> (FlagType<?>) type)
                .collect(Collectors.toSet()));
    }

    /**
     * Gets all known flag types in an unmodifiable collection
     *
     * @return A collection of all known flag types
     * @since 1.0.0
     */
    @Override
    public Collection<FlagType<?>> getRegistered() {
        return Collections.unmodifiableCollection(this.flags);
    }

    /**
     * Registers your custom flag type
     *
     * @param type The type to register
     * @since 1.0.0
     */
    @Override
    public void register(@NotNull FlagType<?> type) {
        this.flags.add(type);
    }

    public <F extends Flag, T extends FlagType<F>> Stream<? extends T> getRegistered(Class<T> clazz) {
        return this.flags.stream().filter(clazz::isInstance).map(type -> (T) type);
    }

    /**
     * Gets an instance of the {@link DefaultFlagFile}
     *
     * @return The instance
     * @since 1.0.0
     */
    public DefaultFlagFile getDefaultFlags() {
        return this.defaultFlags;
    }


}
