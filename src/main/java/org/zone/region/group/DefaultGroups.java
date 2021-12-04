package org.zone.region.group;

import org.zone.ZonePlugin;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.TreeSet;

public final class DefaultGroups {

    public static final SimpleGroup OWNER;
    public static final SimpleGroup HOME_OWNER;
    public static final SimpleGroup VISITOR;

    static {
        VISITOR = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(), "visitor", "Visitor", null, false);
        HOME_OWNER = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(), "home_owner", "Owner", VISITOR);
        OWNER = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(), "owner", "Owner", HOME_OWNER);

        HOME_OWNER.add(GroupKeys.HOME_OWNER);
        HOME_OWNER.add(GroupKeys.INTERACT_DOOR);

        OWNER.add(GroupKeys.BLOCK_BREAK);
        OWNER.add(GroupKeys.OWNER);
    }

    private DefaultGroups() {
    }

    public static TreeSet<SimpleGroup> createDefaultGroups() {
        return new TreeSet<>(Arrays.asList(OWNER, HOME_OWNER, VISITOR));
    }
}
