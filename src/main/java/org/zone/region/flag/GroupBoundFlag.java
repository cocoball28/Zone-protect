package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.meta.MembersFlag;
import org.zone.region.group.Group;

import java.util.Optional;

public interface GroupBoundFlag<T, F> extends Flag<T, F> {

    interface Single<T> extends Flag.Single<T>, GroupBoundFlag<T, Optional<T>> {

    }

    @NotNull String getGroupId();

    void setGroupId(@NotNull String groupId);

    default void setGroup(@NotNull Group group) {
        this.setGroupId(group.getId());
    }


    default Optional<Group> getGroup(@NotNull MembersFlag flag) {
        return flag
                .getGroups()
                .parallelStream()
                .filter(group -> group.getId().equals(this.getGroupId()))
                .findAny();
    }

    default boolean hasPermission(@NotNull MembersFlag flag, @NotNull Group group) {
        Optional<Group> opGroup = this.getGroup(flag);
        if (opGroup.isEmpty()) {
            return false;
        }
        while (!group.equals(opGroup.get())) {
            Optional<Group> opParent = group.getParent();
            if (opParent.isEmpty()) {
                return false;
            }
            group = opParent.get();
        }
        return true;
    }
}
