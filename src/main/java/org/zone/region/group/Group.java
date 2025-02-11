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

/**
 * A group of players found within a zone. Think of this like a Permissions group only specifically
 * for a zone. A group can have GroupKeys which act like permissions for that group within the
 * zone.
 */
public interface Group extends Identifiable, Comparable<Group> {

    /**
     * Gets the parent of this group. All group keys are inherited from all parents
     *
     * @return The parent to this group
     */
    @NotNull Optional<Group> getParent();

    /**
     * Sets the parent of this group
     *
     * @param group The new parent of the group
     */
    void setParent(@NotNull Group group);

    /**
     * Checks if the group can be removed from the zone. All groups should be removable unless
     * there is a specific reason for it such as the visitor group
     *
     * @return if the group can be removed
     */
    boolean canBeRemoved();

    /**
     * Gets all the groupKeys for this group
     *
     * @return a collection of the group keys
     */
    @NotNull Collection<GroupKey> getKeys();

    /**
     * Adds a groupKey to this group. Please note you should use
     * {@link org.zone.region.flag.meta.member.MembersFlag#addKey(Group, GroupKey)} instead where
     * possible as that will prevent duplicates
     *
     * @param key The group key
     *
     * @return if the key was added
     */
    default boolean add(@NotNull GroupKey key) {
        return this.getKeys().add(key);
    }

    /**
     * Adds all groupKeys to this group. Please note you should use
     * {@link org.zone.region.flag.meta.member.MembersFlag#addKey(Group, GroupKey)} instead where
     * possible as that will prevent duplicates
     *
     * @param keys The keys to add
     *
     * @return if the key was added
     */
    default boolean addAll(@NotNull Collection<? extends GroupKey> keys) {
        return this.getKeys().addAll(keys);
    }

    /**
     * Removes the GroupKey from this group
     *
     * @param key the key to remove
     *
     * @return if the key was removed
     */
    default boolean remove(@NotNull GroupKey key) {
        return this.getKeys().remove(key);
    }

    /**
     * Checks if the groupKey was found within this group
     *
     * @param key The groupKey to check
     *
     * @return true if the group contains the groupKey
     */
    default boolean contains(GroupKey key) {
        if (this.getKeys().contains(key)) {
            return true;
        }
        return this.getParent().map(parent -> parent.contains(key)).orElse(false);
    }

    /**
     * Gets all parents as a stream
     *
     * @return all parent groups as a stream
     */
    default @NotNull Stream<Group> getImplements() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ImplementedGroupIterator(
                this), Spliterator.ORDERED), false);
    }

    /**
     * All GroupKeys assigned to this and its parents
     *
     * @return All groupKeys
     */
    default @NotNull Collection<GroupKey> getAllKeys() {
        return this
                .getImplements()
                .flatMap(g -> g.getKeys().stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if the provided group is a parent of this group
     *
     * @param group the group to check
     *
     * @return true if its a parent
     */
    default boolean inherits(@NotNull Group group) {
        return this.getImplements().anyMatch(g -> g.equals(group));
    }

    @Override
    default int compareTo(@NotNull Group o) {
        if (o.equals(this)) {
            return 0;
        }
        return this.inherits(o) ? -1 : this.getId().compareTo(o.getId());
    }
}
