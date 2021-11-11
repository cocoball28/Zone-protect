package org.zone.region.flag.meta;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.util.*;
import java.util.stream.Collectors;

public class MembersFlag implements Flag.Map<Group, Collection<UUID>> {

    public static final MembersFlag DEFAULT = new MembersFlag(SimpleGroup.VISITOR, SimpleGroup.HOME_OWNER,
            SimpleGroup.OWNER);

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

    public void addMember(Group group, UUID uuid) {
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
        for(java.util.Map.Entry<Group, Collection<UUID>> entry : this.groups.entrySet()){
            for(UUID user : entry.getValue()){
                if(user.equals(uuid)){
                    return entry.getKey();
                }
            }
        }
        return SimpleGroup.VISITOR;

        /*return this
                .groups
                .entrySet()
                .parallelStream()
                .filter(entry -> {
                    return entry.getValue().parallelStream().anyMatch(uuid2 -> uuid2.toString().equals(uuid.toString()));
                })
                .findFirst()
                .map(java.util.Map.Entry::getKey)
                .orElse(SimpleGroup.VISITOR);*/
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

    @Override
    public @NotNull java.util.Map<Group, Collection<UUID>> getValue() {
        return this.groups;
    }

    @Override
    public void setValue(@Nullable java.util.Map<Group, Collection<UUID>> flag) {
        this.groups.clear();
        if (flag!=null) {
            this.groups.putAll(flag);
        }
    }

    @Override
    public void removeValue() {
        this.groups.clear();
    }
}
