package org.zone.region.group;

import org.jetbrains.annotations.NotNull;
import org.zone.Identifiable;

import java.util.Optional;

public interface Group extends Identifiable, Comparable<Group> {

    Optional<Group> getParent();

    void setParent(@NotNull Group group);

    boolean canBeRemoved();

    default boolean inherits(Group group) {
        if (group.equals(this)) {
            return true;
        }
        Optional<Group> opParent = this.getParent();
        while (opParent.isPresent()) {
            if (opParent.get().equals(this)) {
                return true;
            }
            opParent = opParent.get().getParent();
        }
        return false;
    }

    @Override
    default int compareTo(@NotNull Group o) {
        if (o.equals(this)) {
            return 0;
        }
        return this.inherits(o) ? -1:this.getId().compareTo(o.getId());
    }
}
