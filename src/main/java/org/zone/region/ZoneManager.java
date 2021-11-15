package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;
import org.zone.ZonePlugin;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.regions.Region;
import org.zone.region.regions.type.PointRegion;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ZoneManager {

    private final @NotNull Collection<Zone> zones = new TreeSet<>(Comparator.comparing(Identifiable::getId));

    private static final Object[] NAME = {"Name"};
    private static final Object[] FLAGS = {"Flags"};
    private static final Object[] PARENT = {"Parent"};
    private static final Object[] REGION_PART_ONE_X = {"Region", "One", "X"};
    private static final Object[] REGION_PART_ONE_Y = {"Region", "One", "Y"};
    private static final Object[] REGION_PART_ONE_Z = {"Region", "One", "Z"};
    private static final Object[] REGION_PART_TWO_X = {"Region", "Two", "X"};
    private static final Object[] REGION_PART_TWO_Y = {"Region", "Two", "Y"};
    private static final Object[] REGION_PART_TWO_Z = {"Region", "Two", "Z"};
    private static final Object[] REGION_WORLD = {"Region", "World"};


    public @NotNull Collection<Zone> getZones() {
        return Collections.unmodifiableCollection(this.zones);
    }

    public @NotNull Optional<Zone> getZone(PluginContainer container, String key) {
        return this.getZone(container.metadata().id() + ":" + key);
    }

    public @NotNull Optional<Zone> getZone(String id) {
        return this.getZones().parallelStream().filter(zone -> zone.getId().equals(id)).findAny();
    }

    public @NotNull Collection<Zone> getZone(@Nullable World<?, ?> world, @NotNull Vector3d worldPos) {
        return this.getZones().stream().filter(zone -> zone.inRegion(world, worldPos)).collect(Collectors.toSet());
    }

    public @NotNull Optional<Zone> getPriorityZone(@Nullable World<?, ?> world, @NotNull Vector3d worldPos) {
        Collection<Zone> zones = this.getZone(world, worldPos);
        if (zones.isEmpty()) {
            return Optional.empty();
        }
        for (Zone zone : zones) {
            if (zone.getParent().isPresent()) {
                zones.remove(zone.getParent().get());
            }
        }
        if (zones.size()==1) {
            return Optional.of(zones.iterator().next());
        }
        Collection<Zone> sortedZone = new TreeSet<>(Comparator.comparing(Identifiable::getId));
        sortedZone.addAll(zones);
        return Optional.of(sortedZone.iterator().next());
    }

    public void register(Zone zone) {
        this.zones.add(zone);
    }

    public Zone load(File file) throws ConfigurateException {
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder().file(file).build();
        ConfigurationNode node = loader.load();
        String name = node.node(NAME).getString();
        if (name==null) {
            throw new ConfigurateException("Name of zone is missing in " + file.getPath());
        }
        String parentId = node.node(PARENT).getString();
        String fileName = file.getName();
        ZoneBuilder builder = new ZoneBuilder()
                .setName(name)
                .setKey(fileName.substring(0, fileName.length() - 5))
                .setParentId(parentId);
        String worldId = node.node(REGION_WORLD).getString();
        if (worldId==null) {
            throw new ConfigurateException("World of zone is missing in " + file.getPath());
        }
        ResourceKey worldKey = ResourceKey.resolve(worldId);
        String pluginStr = file.getParentFile().getName();
        Optional<PluginContainer> opPlugin = Sponge.pluginManager().plugin(pluginStr);
        if (opPlugin.isEmpty()) {
            throw new ConfigurateException("Plugin cannot be found: " + pluginStr);
        }
        builder.setContainer(opPlugin.get());
        if (!node.node(REGION_PART_TWO_Z).isNull()
                && !node.node(REGION_PART_TWO_Y).isNull()
                && !node.node(REGION_PART_TWO_X).isNull()
                && !node.node(REGION_PART_ONE_Z).isNull()
                && !node.node(REGION_PART_ONE_Y).isNull()
                && !node.node(REGION_PART_ONE_X).isNull()) {
            //region PointRegion
            Vector3i one = new Vector3i(node.node(REGION_PART_ONE_X).getInt(), node.node(REGION_PART_ONE_Y).getInt(),
                    node.node(REGION_PART_ONE_Z).getInt());
            Vector3i two = new Vector3i(node.node(REGION_PART_TWO_X).getInt(), node.node(REGION_PART_TWO_Y).getInt(),
                    node.node(REGION_PART_TWO_Z).getInt());
            Region region = new PointRegion(worldKey, one, two);
            builder.setRegion(region);
        }
        Map<Object, ? extends ConfigurationNode> flagPlugins = node.node(FLAGS).childrenMap();
        Map<FlagType<?>, ConfigurationNode> types = new TreeMap<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> flagPluginNode : flagPlugins.entrySet()) {
            for (Map.Entry<Object, ? extends ConfigurationNode> keyNode : flagPluginNode.getValue().childrenMap().entrySet()) {
                Optional<FlagType<?>> opFlag = ZonePlugin
                        .getZonesPlugin()
                        .getFlagManager()
                        .getRegistered()
                        .parallelStream()
                        .filter(type -> type.getPlugin().metadata().id().equalsIgnoreCase(flagPluginNode.getKey().toString()))
                        .filter(type -> type.getKey().equalsIgnoreCase(keyNode.getKey().toString()))
                        .findFirst();
                if (opFlag.isEmpty()) {
                    ZonePlugin.getZonesPlugin().getLogger().error("Could not load flag: Unknown plugin of " + flagPluginNode.getKey().toString());
                    continue;
                }
                if (types.containsKey(opFlag.get())) {
                    throw new IllegalStateException("Two or more flag keys found to be the same: " + opFlag.get());
                }
                types.put(opFlag.get(), keyNode.getValue());
            }
        }
        for (Map.Entry<FlagType<?>, ConfigurationNode> entry : types.entrySet()) {
            try {
                Flag<?, ?> flag = entry.getKey().load(entry.getValue());
                builder.addFlags(flag);
            } catch (IOException e) {
                ZonePlugin.getZonesPlugin().getLogger().error("Could not load flag: " + e.getMessage());
            }

        }
        return builder.build();
    }

    public File save(Zone zone) throws ConfigurateException {
        File file = new File("config/zone/zones/" + zone.getPlugin().metadata().id() + "/" + zone.getKey() + ".conf");
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder().file(file).build();
        ConfigurationNode node = loader.createNode();

        node.node(NAME).set(zone.getName());
        if (zone.getParent().isPresent()) {
            node.node(PARENT).set(zone.getParent().get().getId());
        }
        for (Flag<?, ?> flag : zone.getFlags()) {
            ConfigurationNode flagNode = node
                    .node(FLAGS)
                    .node(flag.getType().getPlugin().metadata().id())
                    .node(flag.getType().getKey());
            try {
                flag.save(flagNode);
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        }
        Region region = zone.getRegion();
        if (region instanceof PointRegion pointRegion) {
            Vector3i one = pointRegion.getPointOne();
            Vector3i two = pointRegion.getPointTwo();
            node.node(REGION_PART_ONE_X).set(one.x());
            node.node(REGION_PART_ONE_Y).set(one.y());
            node.node(REGION_PART_ONE_Z).set(one.z());
            node.node(REGION_PART_TWO_X).set(two.x());
            node.node(REGION_PART_TWO_Y).set(two.y());
            node.node(REGION_PART_TWO_Z).set(two.z());
            node.node(REGION_WORLD).set(pointRegion.getWorldKey().asString());
        } else {
            throw new SerializationException("Unknown region type of " + region.getClass().getName());
        }
        loader.save(node);
        return file;
    }
}
