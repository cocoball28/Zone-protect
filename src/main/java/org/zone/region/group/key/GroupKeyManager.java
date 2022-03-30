package org.zone.region.group.key;

import org.jetbrains.annotations.NotNull;
import org.zone.IdentifiableManager;
import org.zone.ZonePlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Gets the Manager for GroupKeys.
 *
 * Use {@link ZonePlugin#getGroupKeyManager()} to get an instance
 * @since 1.0.1
 */
public class GroupKeyManager implements IdentifiableManager<GroupKey> {

    @SuppressWarnings("vanilla-only")
    private final Collection<GroupKey> keys = new HashSet<>(Arrays.asList(GroupKeys.values()));

    @Override
    public Collection<GroupKey> getRegistered() {
        return Collections.unmodifiableCollection(this.keys);
    }

    /**
     * Registers a new GroupKey
     *
     * @param key The new key
     * @since 1.0.1
     */
    public void register(@NotNull GroupKey key) {
        this.keys.add(key);
    }

    /**
     * Gets all the keys
     *
     * @return a collection of all the keys
     *
     * @deprecated use {@link #getRegistered()}
     */
    @Deprecated(forRemoval = true, since = "1.0.1")
    public Collection<GroupKey> getKeys() {
        return Collections.unmodifiableCollection(this.keys);
    }
}
