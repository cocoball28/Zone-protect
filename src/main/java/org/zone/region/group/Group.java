package org.zone.region.group;

import org.jetbrains.annotations.NotNull;
import org.zone.Identifiable;
import org.zone.region.group.key.GroupKey;

import java.util.Collection;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Group extends Identifiable, Comparable<Group> {

    Optional<Group> getParent();

    void setParent(@NotNull Group group);

    boolean canBeRemoved();

    Collection<GroupKey> getKeys();

    default boolean add(GroupKey key) {
        return this.getKeys().add(key);
    }

    default boolean remove(GroupKey key) {
        return this.getKeys().remove(key);
    }

    default Stream<Group> getImplements() {
        return StreamSupport
                .stream(Spliterators
                                .spliteratorUnknownSize(
                                        new ImplementedGroupIterator(this),
                                        Spliterator.ORDERED),
                        false);
    }


    default Collection<GroupKey> getAllKeys() {
        return this
                .getImplements()
                .flatMap(g -> g.getKeys().stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    default boolean inherits(Group group) {
        return this.getImplements().anyMatch(g -> g.equals(group));
    }

    @Override
    default int compareTo(@NotNull Group o) {
        if (o.equals(this)) {
            return 0;
        }
        return this.inherits(o) ? -1:this.getId().compareTo(o.getId());
    }
}
