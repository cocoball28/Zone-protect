package org.zone.region.flag;

import org.zone.region.group.Group;

import java.util.Optional;

public interface GroupBoundFlag extends Flag {

    Group getGroup();

    default boolean hasPermission(Group group) {
        while (!group.equals(this.getGroup())) {
            Optional<Group> opParent = group.getParent();
            if (opParent.isEmpty()) {
                return false;
            }
            group = opParent.get();
        }
        return true;
    }
}
