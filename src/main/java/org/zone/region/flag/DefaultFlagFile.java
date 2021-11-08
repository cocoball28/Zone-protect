package org.zone.region.flag;

import org.spongepowered.api.Sponge;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;
import org.zone.ZonePlugin;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultFlagFile {

    public static final File FILE = new File("config/zones/DefaultZone.conf");
    private final HoconConfigurationLoader loader;
    private final ConfigurationNode node;

    public DefaultFlagFile() {
        loader = HoconConfigurationLoader
                .builder()
                .file(FILE)
                .build();

        ConfigurationNode node1;
        try {
            node1 = this.loader.load();
        } catch (ConfigurateException e) {
            node1 = this.loader.createNode();
            this.updateFile();
        }
        this.node = node1;
    }

    private void updateFile() {
        try {
            if (!FILE.exists()) {
                FILE.getParentFile().mkdirs();

                FILE.createNewFile();
            }
            for (FlagType<? extends Flag> type : ZonePlugin.getZonesPlugin().getFlagManager().getRegistered()) {
                Optional<? extends Flag> opFlag = this.loadDefault(type);
                if (opFlag.isEmpty()) {
                    this.removeDefault(type);
                    continue;
                }
                this.setDefault(opFlag.get());
            }
            this.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <F extends Flag, T extends FlagType<F>> Optional<F> loadDefault(T type) {
        try {
            return Optional.of(type.load(this.node.node("flags", type.getPlugin().metadata().id(), type.getKey())));
        } catch (IOException e) {
            return type.createDefaultFlag();
        }
    }

    public <F extends Flag, T extends FlagType<F>> void setDefault(F flag) throws IOException {
        T type = (T) flag.getType();
        type.save(this.node.node("flags", type.getPlugin().metadata().id(),
                type.getKey()), flag);
    }

    public void removeDefault(FlagType<? extends Flag> type) throws IOException {
        type.save(this.node.node("flags"), null);
    }

    public void save() throws ConfigurateException {
        this.loader.save(this.node);
    }

    public Collection<Group> loadGroups() {
        ConfigurationNode groupNodes = this.node.node("groups");
        Collection<PluginContainer> plugins =
                groupNodes
                        .childrenList()
                        .parallelStream()
                        .map(node -> Sponge.pluginManager().plugin(node.key().toString()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
        Map<PluginContainer, List<? extends ConfigurationNode>> map = plugins
                .parallelStream()
                .collect(Collectors.toMap(pluginContainer -> pluginContainer,
                        pluginContainer -> groupNodes.node(pluginContainer.metadata().id()).childrenList()));
        long count = map.values().parallelStream().flatMap(Collection::parallelStream).count();

        Collection<Group> groups = new TreeSet<>(Comparator.comparing(Identifiable::getId));

        while (map.size()!=count) {
            for (Map.Entry<PluginContainer, List<? extends ConfigurationNode>> entry : map.entrySet()) {
                for (ConfigurationNode node : entry.getValue()) {
                    String name = node.node("name").getString();
                    String parentString = node.node("parent").getString();
                    if (name==null) {
                        System.err.println("No name for " + entry.getKey().metadata().id() + ":" + node.key());
                        continue;
                    }
                    if (parentString==null) {
                        if (entry.getKey().equals(SimpleGroup.VISITOR.getKey())) {
                            groups.add(SimpleGroup.VISITOR);
                            continue;
                        }
                        groups.add(new SimpleGroup(entry.getKey(), node.key().toString(), name, SimpleGroup.VISITOR));
                        continue;
                    }
                    for (Group group : groups) {
                        if (group.getId().equals(parentString)) {
                            groups.add(new SimpleGroup(entry.getKey(), node.key().toString(), name, group));
                            break;
                        }
                    }
                }
            }
        }

        if (groups.isEmpty()) {
            groups.addAll(SimpleGroup.createDefaultGroup());
        }
        return groups;
    }
}
