package org.zone.region.group;

import org.jetbrains.annotations.NotNull;
import org.zone.Identifiable;

import java.util.Optional;

public interface Group extends Identifiable, Comparable<Group> {

    Optional<Group> getParent();

    void setParent(@NotNull Group group);

    boolean canBeRemoved();

    @Override
    default int compareTo(@NotNull Group o) {
        if (o.equals(this)) {
            return 0;
        }
        Optional<Group> opParent = this.getParent();
        while (opParent.isPresent()) {
            if (opParent.get().equals(this)) {
                return 1;
            }
            opParent = opParent.get().getParent();
        }
        return -1;
    }
}
