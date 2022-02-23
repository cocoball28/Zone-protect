package org.zone.region.group.key;

import org.zone.ZonePlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Gets the Manager for GroupKeys.
 *
 * Use {@link ZonePlugin#getGroupKeyManager()} to get an instance
 */
public class GroupKeyManager {

    @SuppressWarnings("vanilla-only")
    private final Collection<GroupKey> keys = new HashSet<>(Arrays.asList(GroupKeys.values()));

    public void register(GroupKey key) {
        this.keys.add(key);
    }

    public Collection<GroupKey> getKeys() {
        return Collections.unmodifiableCollection(this.keys);
    }
}
