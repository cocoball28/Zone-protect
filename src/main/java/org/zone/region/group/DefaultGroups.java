package org.zone.region.group;

import org.zone.ZonePlugin;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Gets the default groups for the zone plugin
 */
public final class DefaultGroups {

    public static final SimpleGroup OWNER;
    public static final SimpleGroup NEW_MEMBER;
    public static final SimpleGroup HOME_OWNER;
    public static final SimpleGroup VISITOR;

    static {
        VISITOR = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(),
                "visitor",
                "Visitor",
                null,
                false);
        NEW_MEMBER = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(),
                "member",
                "Member",
                VISITOR);
        HOME_OWNER = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(),
                "home_owner",
                "Home owner",
                NEW_MEMBER);
        OWNER = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(),
                "owner",
                "Owner",
                HOME_OWNER);

        HOME_OWNER.add(GroupKeys.HOME_OWNER);
        HOME_OWNER.add(GroupKeys.INTERACT_DOOR);

        NEW_MEMBER.add(GroupKeys.NEW_MEMBER);

        OWNER.add(GroupKeys.OWNER);
        OWNER.add(GroupKeys.BLOCK_BREAK);
        OWNER.add(GroupKeys.BLOCK_PLACE);
        OWNER.add(GroupKeys.PLAYER_PREVENTION);
        OWNER.add(GroupKeys.INTERACT_ITEMFRAME);
        OWNER.add(GroupKeys.ENTITY_DAMAGE_PLAYER);
        OWNER.add(GroupKeys.PLAYER_FALL_DAMAGE);
    }

    private DefaultGroups() {
    }

    /**
     * Creates the default groups
     *
     * @return A TreeSet of the default groups
     */
    public static TreeSet<SimpleGroup> createDefaultGroups() {
        return new TreeSet<>(Arrays.asList(OWNER, HOME_OWNER, NEW_MEMBER, VISITOR));
    }
}
