package org.zone.region.group.key;

import java.util.*;

public class GroupKeyManager {

    private final Set<GroupKey> keys = new HashSet<>(Arrays.asList(GroupKeys.values()));

    public void register(GroupKey key) {
        this.keys.add(key);
    }

    public Collection<GroupKey> getKeys() {
        return Collections.unmodifiableCollection(this.keys);
    }
}
