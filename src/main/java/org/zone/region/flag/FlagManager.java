package org.zone.region.flag;

import org.zone.Identifiable;
import org.zone.ZonePlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * The manager of all flags, this is where you should register your custom flag or get all flags.
 * <p>
 * use {@link ZonePlugin#getFlagManager()} to get a instance of this flag manager
 */
public class FlagManager {

    private final Collection<FlagType<?>> flags = new TreeSet<>(Comparator.comparing(Identifiable::getId));
    private final DefaultFlagFile defaultFlags = new DefaultFlagFile();

    public FlagManager() {
        this.flags.add(FlagTypes.PREVENT_MONSTER);
        this.flags.add(FlagTypes.MEMBERS);
        this.flags.add(FlagTypes.DOOR_INTERACTION);
        this.flags.add(FlagTypes.BLOCK_BREAK);
        this.flags.add(FlagTypes.ECO);
        this.flags.add(FlagTypes.EDITING);
        this.flags.add(FlagTypes.BLOCK_PLACE);
        this.flags.add(FlagTypes.GREETINGS_FLAG_TYPE);
        this.flags.add(FlagTypes.PREVENT_PLAYERS_FLAG_TYPE);
    }

    /**
     * Gets all known flag types in a unmodifiable collection
     *
     * @return A collection of all known flag types
     */
    public Collection<FlagType<?>> getRegistered() {
        return Collections.unmodifiableCollection(this.flags);
    }

    /**
     * Registers your custom flag type
     *
     * @param type The type to register
     */
    public void register(FlagType<?> type) {
        this.flags.add(type);
    }

    /**
     * Gets a instance of the {@link DefaultFlagFile}
     *
     * @return The instance
     */
    public DefaultFlagFile getDefaultFlags() {
        return this.defaultFlags;
    }


}
