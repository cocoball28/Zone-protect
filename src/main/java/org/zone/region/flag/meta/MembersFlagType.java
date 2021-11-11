package org.zone.region.flag.meta;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MembersFlagType implements FlagType<MembersFlag> {

    public static final String NAME = "Members";
    public static final String KEY = "members";

    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return KEY;
    }

    @Override
    public @NotNull MembersFlag load(@NotNull ConfigurationNode node) throws IOException {
        Set<PluginContainer> plugins =
                node
                        .childrenMap()
                        .keySet()
                        .parallelStream()
                        .map(key -> Sponge.pluginManager().plugin(key.toString()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
        Map<PluginContainer, Collection<? extends ConfigurationNode>> keys = new HashMap<>();
        for (PluginContainer container : plugins) {
            keys.put(container, node.node(container.metadata().id()).childrenMap().values());
        }

        int totalSize = (int) keys.values().parallelStream().flatMap(Collection::parallelStream).count();
        if (totalSize==0) {
            throw new IOException("No groups found");
        }
        Map<Group, Collection<UUID>> groups = new HashMap<>();
        groups.put(SimpleGroup.VISITOR, Collections.emptyList());
        Integer added = null;

        while (groups.size()!=totalSize && (added==null || added!=0)) {
            added = 0;
            for (Map.Entry<PluginContainer, Collection<? extends ConfigurationNode>> entry : keys.entrySet()) {
                for (ConfigurationNode groupNode : entry.getValue()) {
                    String name = groupNode.node("name").getString();
                    String parentString = groupNode.node("parent").getString();
                    String id = entry.getKey().metadata().id() + ":" + groupNode.key().toString();
                    if (groups.keySet().parallelStream().anyMatch(g -> g.getId().equals(id))) {
                        continue;
                    }
                    List<String> ids = groupNode
                            .node("users")
                            .getList(String.class);
                    if (ids==null) {
                        continue;
                    }
                    Set<UUID> users =
                            ids
                                    .parallelStream()
                                    .map(UUID::fromString)
                                    .collect(Collectors.toSet());
                    if (name==null) {
                        continue;
                    }
                    if (parentString==null) {
                        continue;
                    }
                    Optional<Group> opParent = groups
                            .keySet()
                            .parallelStream()
                            .filter(g -> g
                                    .getId()
                                    .equals(parentString))
                            .findFirst();
                    if (opParent.isEmpty()) {
                        continue;
                    }
                    groups.put(
                            new SimpleGroup(
                                    entry.getKey(),
                                    groupNode.key().toString(),
                                    name,
                                    opParent.get()),
                            users);
                    added++;
                }
            }
        }
        if (groups.size()==1) {
            throw new IOException("Could not find groups");
        }
        if (added==0) {
            ZonePlugin.getZonesPlugin().getLogger().warn("Could not load some groups for a zone.");
        }
        return new MembersFlag(groups);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable MembersFlag save) throws IOException {
        if (save==null) {
            node.set(null);
            return;
        }
        for (Map.Entry<Group, Collection<UUID>> entry : save.getValue().entrySet()) {
            ConfigurationNode groupNode = node
                    .node(entry
                            .getKey()
                            .getPlugin()
                            .metadata()
                            .id(), entry
                            .getKey()
                            .getKey());
            groupNode.node("name").set(entry.getKey().getName());
            groupNode.node("users").set(entry.getValue().stream().map(UUID::toString).sorted().collect(Collectors.toList()));
            Optional<Group> opParent = entry.getKey().getParent();
            if (opParent.isPresent()) {
                groupNode.node("parent").set(opParent.get().getId());
            }
        }
    }

    @Override
    public boolean canApply(Zone zone) {
        return true;
    }

    @Override
    public Optional<MembersFlag> createDefaultFlag() {
        return Optional.of(MembersFlag.DEFAULT);
    }
}
