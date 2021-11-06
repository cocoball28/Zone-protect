package org.zone.region.group;

import org.zone.Identifiable;

import java.util.Optional;

public interface Group extends Identifiable {

    Optional<Group> getParent();
}
