package org.zone.region.flag.meta;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.DefaultGroups;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKey;

import java.util.*;
import java.util.stream.Collectors;

public class MembersFlag implements Flag {

    public static final MembersFlag DEFAULT = new MembersFlag(DefaultGroups.createDefaultGroups());

    private final java.util.Map<Group, Collection<UUID>> groups = new HashMap<>();

    public MembersFlag() {
        this(DEFAULT);
    }

    public MembersFlag(Group... groups) {
        this(Arrays.asList(groups));
    }

    public MembersFlag(Collection<? extends Group> groups) {
        this(groups.stream().collect(Collectors.toMap(g -> g, g -> new HashSet<>())));
    }

    public MembersFlag(MembersFlag flag) {
        this(flag.groups);
    }

    public MembersFlag(java.util.Map<? extends Group, ? extends Collection<UUID>> map) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Cannot have no groups");
        }

        this.groups.putAll(map);
    }

    public Optional<Group> getGroup(GroupKey key) {
        return this
                .groups
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getKey)
                .filter(group -> group.getKeys().contains(key))
                .findAny();
    }

    public void removeKey(GroupKey key) {
        this.groups.keySet().forEach(group -> group.remove(key));
    }

    public void addKey(Group group, GroupKey key) {
        this.removeKey(key);
        group.add(key);
    }

    public void addMember(Group group, UUID uuid) {
        this.removeMember(uuid);
        if (group.equals(DefaultGroups.VISITOR)) {
            return;
        }
        Collection<UUID> set = this.groups.getOrDefault(group, new HashSet<>());
        set.add(uuid);
        if (this.groups.containsKey(group)) {
            this.groups.replace(group, set);
            return;
        }
        this.groups.put(group, set);
    }

    public Collection<UUID> getMembers() {
        return this
                .groups
                .entrySet()
                .parallelStream()
                .flatMap(e -> e.getValue().parallelStream())
                .collect(Collectors.toUnmodifiableSet());
    }

    public Collection<UUID> getMembers(Group group) {
        return this.groups.getOrDefault(group, Collections.emptyList());
    }

    public Set<Group> getGroups() {
        return this.groups.keySet();
    }

    public Group getGroup(UUID uuid) {
        for (java.util.Map.Entry<Group, Collection<UUID>> entry : this.groups.entrySet()) {
            for (UUID user : entry.getValue()) {
                if (user.equals(uuid)) {
                    return entry.getKey();
                }
            }
        }
        return DefaultGroups.VISITOR;
    }

    public void removeMember(UUID uuid) {
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
