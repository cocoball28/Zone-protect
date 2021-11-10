package org.zone.region.flag.meta;

import org.jetbrains.annotations.NotNull;
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
                        .childrenList()
                        .parallelStream()
                        .map(node2 -> node2.key().toString())
                        .map(key -> Sponge.pluginManager().plugin(key))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
        Map<PluginContainer, Collection<? extends ConfigurationNode>> keys = new HashMap<>();
        for (PluginContainer container : plugins) {
            keys.put(container, node.node(container.metadata().id()).childrenList());
        }

        int totalSize = (int) keys.values().parallelStream().flatMap(Collection::parallelStream).count();
        Map<Group, Collection<UUID>> groups = new HashMap<>();

        while (groups.size()!=totalSize) {
            for (Map.Entry<PluginContainer, Collection<? extends ConfigurationNode>> entry : keys.entrySet()) {
                for (ConfigurationNode groupNode : entry.getValue()) {
                    String name = groupNode.node("name").getString();
                    String parentString = groupNode.node("parent").getString();
                    Set<UUID> users =
                            groupNode
                                    .node("users")
                                    .getList(String.class)
                                    .parallelStream()
                                    .map(UUID::fromString)
                                    .collect(Collectors.toSet());
                    if (name==null) {
                        continue;
                    }
                    if (parentString==null) {
                        if (name.equals(SimpleGroup.VISITOR.getName()) &&
                                groupNode.key().toString().equals(SimpleGroup.VISITOR.getKey())) {
                            groups.put(SimpleGroup.VISITOR, users);
                        }
                        continue;
                    }
                    Optional<Group> opParent = groups
                            .keySet()
                            .parallelStream()
                            .filter(g -> g
                                    .getId()
                                    .equals(entry
                                            .getKey()
                                            .metadata()
                                            .id()
                                            + ":"
                                            + groupNode
                                            .key()
                                            .toString()))
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
                }
            }
        }
        if (groups.isEmpty()) {
            throw new IOException("Could not find groups");
        }
        return new MembersFlag(groups);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @NotNull MembersFlag save) throws IOException {
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
            groupNode.node("users").set(entry.getValue());
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
