package org.zone.region.flag.meta.member;

import org.jetbrains.annotations.NotNull;
import org.zone.ZonePlugin;
import org.zone.config.node.ZoneNodes;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.DefaultGroups;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Flag used to hold all members
 */
public class MembersFlag implements Flag {

    private final java.util.Map<Group, Collection<UUID>> groups = new HashMap<>();
    public static final MembersFlag DEFAULT = new MembersFlag(DefaultGroups.createDefaultGroups());

    public MembersFlag() {
        this(DEFAULT);
    }

    public MembersFlag(@NotNull Group... groups) {
        this(Arrays.asList(groups));
    }

    public MembersFlag(@NotNull Collection<? extends Group> groups) {
        this(groups.stream().collect(Collectors.toMap(g -> g, g -> new HashSet<>())));
    }

    public MembersFlag(@NotNull MembersFlag flag) {
        this(flag.groups);
    }

    public MembersFlag(@NotNull java.util.Map<? extends Group, ? extends Collection<UUID>> map) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Cannot have no groups");
        }

        this.groups.putAll(map);
    }

    /**
     * Gets the power level this zone has
     *
     * @return The power level this zone has
     */
    public long getPowerLevel() {
        return this.groups.values().parallelStream().flatMap(Collection::parallelStream).count();
    }

    /**
     * Gets a group by a specified GroupKey, {@link Optional#empty()} if no group holds that key
     *
     * @param key The key to check
     *
     * @return The group holding the key, {@link Optional#empty()} if no group holds the specified key
     */
    public Optional<Group> getGroup(@NotNull GroupKey key) {
        return this.groups
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getKey)
                .filter(group -> group.getKeys().contains(key))
                .findAny();
    }

    /**
     * Removes the key from all groups
     *
     * @param key The key to remove
     */
    public void removeKey(@NotNull GroupKey key) {
        this.groups.keySet().forEach(group -> group.remove(key));
    }

    /**
     * Adds a group key to a group
     *
     * @param group the group to have the key
     * @param key   the key to use
     */
    public void addKey(@NotNull Group group, @NotNull GroupKey key) {
        this.removeKey(key);
        group.add(key);
    }

    /**
     * Adds a member to the group
     *
     * @param group The group to use
     * @param uuid  the UUID of the player
     */
    public boolean addMember(@NotNull Group group, @NotNull UUID uuid) {
        if (group.contains(GroupKeys.OWNER)) {
            int size = this.getMembers(group).size() + 1;
            if (ZonePlugin.getZonesPlugin().getConfig().getOrElse(ZoneNodes.MAX_OWNER) < size) {
                return false;
            }
        }
        this.removeMember(uuid);
        if (group.equals(DefaultGroups.VISITOR)) {
            return false;
        }
        Collection<UUID> set = this.groups.getOrDefault(group, new HashSet<>());
        set.add(uuid);
        if (this.groups.containsKey(group)) {
            this.groups.replace(group, set);
            return true;
        }
        this.groups.put(group, set);
        return true;
    }

    /**
     * Gets all members found within this flag
     *
     * @return A collection of members
     */
    public @NotNull Collection<UUID> getMembers() {
        return this.groups
                .entrySet()
                .parallelStream()
                .flatMap(e -> e.getValue().parallelStream())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * gets all the members of a specific group
     *
     * @param group the group to check
     *
     * @return A collection of members for that group
     */
    public @NotNull Collection<UUID> getMembers(@NotNull Group group) {
        return this.groups.getOrDefault(group, Collections.emptyList());
    }

    /**
     * Gets the groups found in this flag
     *
     * @return a set of groups
     */
    public @NotNull Set<Group> getGroups() {
        return this.groups.keySet();
    }

    /**
     * gets the group a player belongs to
     *
     * @param uuid UUID of the player
     *
     * @return The group the player belongs to
     */
    public @NotNull Group getGroup(@NotNull UUID uuid) {
        for (java.util.Map.Entry<Group, Collection<UUID>> entry : this.groups.entrySet()) {
            for (UUID user : entry.getValue()) {
                if (user.equals(uuid)) {
                    return entry.getKey();
                }
            }
        }
        return DefaultGroups.VISITOR;
    }

    /**
     * sets a player into the visitor group
     *
     * @param uuid the UUID of the player
     */
    public void removeMember(@NotNull UUID uuid) {
        for (Collection<UUID> uuids : this.groups.values()) {
            if (!uuids.contains(uuid)) {
                continue;
            }
            uuids.remove(uuid);
            return;
        }
    }

    @Override
    public @NotNull MembersFlagType getType() {
        return FlagTypes.MEMBERS;
    }

    public @NotNull java.util.Map<Group, Collection<UUID>> getGroupMapping() {
        return this.groups;
    }
}
