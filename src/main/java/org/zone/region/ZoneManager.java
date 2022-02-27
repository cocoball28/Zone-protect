package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.plugin.PluginContainer;
import org.zone.Identifiable;
import org.zone.Serializable;
import org.zone.ZonePlugin;
import org.zone.region.bounds.ChildRegion;
import org.zone.region.bounds.Region;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.utils.Messages;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Gets the manager for zones
 */
public class ZoneManager {

    private final @NotNull Collection<Zone> zones = new LinkedHashSet<>();
    private boolean isBeingWrittenTo;

    private static final Object[] NAME = {"Name"};
    private static final Object[] FLAGS = {"Flags"};
    private static final Object[] PARENT = {"Parent"};
    private static final Object[] REGION = {"Region"};
    private static final Object[] WORLD = {"Region", "World"};

    /**
     * gets all the zones
     *
     * @return A collection of the zones
     */
    public @NotNull Collection<Zone> getZones() {
        while (this.isBeingWrittenTo) {
            /*this bit of code allows the zones to be updated on another thread without causing a
            concurrent error*/
            //noinspection UnnecessaryContinue
            continue;
        }
        return Collections.unmodifiableCollection(this.zones);
    }

    /**
     * Gets a zone that was created by the provided plugin with the key name of the provided
     *
     * @param container The plugin that created the zone
     * @param key       the key name of the zone
     *
     * @return The zone that matches the provided information
     */
    public @NotNull Optional<Zone> getZone(PluginContainer container, String key) {
        return this.getZone(container.metadata().id() + ":" + key);
    }

    /**
     * Gets a zone that has the provided id
     *
     * @param id the id to get
     *
     * @return The zone that has the provided id
     */
    public @NotNull Optional<Zone> getZone(String id) {
        return this.getZones().stream().filter(zone -> zone.getId().equals(id)).findAny();
    }

    public Collection<Zone> getZones(World<?, ?> world) {
        if (world instanceof ServerWorld sWorld) {
            return this
                    .getZones()
                    .stream()
                    .filter(zone -> zone.getWorldKey().isPresent())
                    .filter(zone -> zone.getWorldKey().get().equals(sWorld.key()))
                    .collect(Collectors.toSet());
        }
        return this.getZones();
    }


    /**
     * Gets the zones that are found at a location within a world. Note that zones can cross one
     * and another such as sub zones being instead a parent zone, therefore it returns a collection
     *
     * @param world    The world to check
     * @param worldPos The location to check
     *
     * @return A collection of all the zones found that contain that location
     *
     * @deprecated Typo -> use {@link #getZones()} instead
     */
    @Deprecated(forRemoval = true)
    public @NotNull Collection<Zone> getZone(
            @Nullable World<?, ?> world, @NotNull Vector3d worldPos) {
        return this.getZones(world, worldPos);
    }

    /**
     * Gets the zones that are found at a location within a world. Note that zones can cross one
     * and another such as sub zones being instead a parent zone, therefore it returns a collection
     *
     * @param world    The world to check
     * @param worldPos The location to check
     *
     * @return A collection of all the zones found that contain that location
     */
    public @NotNull Collection<Zone> getZones(
            @Nullable World<?, ?> world, @NotNull Vector3d worldPos) {
        return this
                .getZones()
                .stream()
                .filter(zone -> zone.inRegion(world, worldPos))
                .collect(Collectors.toUnmodifiableSet());
    }

    public @NotNull Collection<Zone> getZonesIntersecting(AABB area) {
        @NotNull Collection<Zone> zones = this.getZones();
        return zones
                .parallelStream()
                .filter(zone -> zone
                        .getRegion()
                        .getTrueChildren()
                        .parallelStream()
                        .anyMatch(region -> region.asAABB().intersects(area)))
                .collect(Collectors.toSet());
    }

    public Optional<Zone> getNearestZone(Location<?, ?> loc, double maxDistance) {
        return this.getNearestZone(loc.world(), loc.position(), maxDistance);
    }

    public Optional<Zone> getNearestZone(Locatable loc, double maxDistance) {
        return this.getNearestZone(loc.world(), loc.location().position(), maxDistance);
    }

    public Optional<Zone> getNearestZoneInView(Player player) {
        if (player instanceof ServerPlayer sPlayer) {
            return this.getNearestZone(player,
                    player
                            .get(Keys.VIEW_DISTANCE)
                            .orElse(sPlayer.world().properties().viewDistance()));
        }
        return this.getNearestZone(player, player.get(Keys.VIEW_DISTANCE).orElse(10));
    }

    public Optional<Zone> getNearestZone(World<?, ?> world, Vector3d pos, double maxDistance) {
        Iterator<Zone> iter = this.getNearZones(world, pos, maxDistance).iterator();
        if (!iter.hasNext()) {
            return Optional.empty();
        }
        Zone zone = iter.next();
        return Optional.ofNullable(zone);
    }

    public List<Zone> getNearZones(World<?, ?> world, Vector3d pos, double maxDistance) {
        return this
                .getZones(world)
                .stream()
                .map(zone -> {
                    Optional<Vector3i> opVector = zone.getRegion().getNearestPosition(pos.toInt());
                    return new AbstractMap.SimpleImmutableEntry<>(zone, opVector);
                })
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> new AbstractMap.SimpleImmutableEntry<>(entry.getKey(),
                        entry.getValue().get().toDouble().distance(pos)))
                .filter(entry -> entry.getValue() < maxDistance)
                .sorted(Map.Entry.comparingByValue())
                .map(AbstractMap.SimpleImmutableEntry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Gets the zone that should be used with interactions with the provided locatable, such as
     * using the sub zone rather than the parent
     *
     * @param locatable The locatable object such as an entity
     *
     * @return The zone to use
     */
    public @NotNull Optional<Zone> getPriorityZone(Locatable locatable) {
        return this.getPriorityZone(locatable.location());
    }

    /**
     * Gets the zone that should be used with interactions at the provided location, such as
     * using the sub zone rather than the parent
     *
     * @param loc The location to compare
     *
     * @return The zone to use. {@link Optional#empty()} when no zone was found at the position
     */
    public @NotNull Optional<Zone> getPriorityZone(Location<? extends World<?, ?>, ?> loc) {
        return this.getPriorityZone(loc.world(), loc.position());
    }

    /**
     * Gets the zone that should be used with interactions at the provided location, such as
     * using the sub zone rather than the parent
     *
     * @param world    The world to compare
     * @param worldPos The location to compare
     *
     * @return The zone to use. {@link Optional#empty()} when no zone was found at the position
     */
    public @NotNull Optional<Zone> getPriorityZone(
            @Nullable World<?, ?> world, @NotNull Vector3d worldPos) {
        Collection<Zone> zones = new HashSet<>(this.getZone(world, worldPos));
        if (zones.isEmpty()) {
            return Optional.empty();
        }
        for (Zone zone : zones) {
            if (zone.getParent().isPresent()) {
                zones.remove(zone.getParent().get());
            }
        }

        if (zones.size() == 1) {
            return Optional.of(zones.iterator().next());
        }
        Collection<Zone> sortedZone = new TreeSet<>(Comparator.comparing(Identifiable::getId));
        sortedZone.addAll(zones);
        return Optional.of(sortedZone.iterator().next());
    }

    /**
     * Registers a new zone
     *
     * @param zone The zone to add
     */
    public synchronized void register(@NotNull Zone zone) {
        this.isBeingWrittenTo = true;
        this.zones.add(zone);
        this.isBeingWrittenTo = false;
    }

    /**
     * Loads a zone from a file
     *
     * @param file The file to load from
     *
     * @return The zone
     *
     * @throws ConfigurateException If you couldn't load
     */
    public synchronized @NotNull Zone load(File file) throws ConfigurateException {
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder().file(file).build();
        ConfigurationNode node = loader.load();
        String name = node.node(NAME).getString();
        if (name == null) {
            throw new ConfigurateException("Name of zone is missing in " + file.getPath());
        }
        String parentId = node.node(PARENT).getString();
        String fileName = file.getName();
        ZoneBuilder builder = new ZoneBuilder()
                .setName(name)
                .setKey(fileName.substring(0, fileName.length() - 5))
                .setParentId(parentId);
        String worldId = node.node(WORLD).getString();
        if (worldId == null) {
            throw new ConfigurateException("World of zone is missing in " + file.getPath());
        }
        ResourceKey worldKey = ResourceKey.resolve(worldId);
        builder.setWorld(worldKey);

        String pluginStr = file.getParentFile().getName();
        Optional<PluginContainer> opPlugin = Sponge.pluginManager().plugin(pluginStr);
        if (opPlugin.isEmpty()) {
            throw new ConfigurateException("Plugin cannot be found: " + pluginStr);
        }
        builder.setContainer(opPlugin.get());
        ChildRegion region = ChildRegion.load(node.node(REGION));
        builder.setRegion(region);
        Map<Object, ? extends ConfigurationNode> flagPlugins = node.node(FLAGS).childrenMap();
        Map<FlagType<?>, ConfigurationNode> types = new TreeMap<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> flagPluginNode : flagPlugins.entrySet()) {
            for (Map.Entry<Object, ? extends ConfigurationNode> keyNode : flagPluginNode
                    .getValue()
                    .childrenMap()
                    .entrySet()) {
                Optional<FlagType<?>> opFlag = ZonePlugin
                        .getZonesPlugin()
                        .getFlagManager()
                        .getRegistered()
                        .parallelStream()
                        .filter(type -> type
                                .getPlugin()
                                .metadata()
                                .id()
                                .equalsIgnoreCase(flagPluginNode.getKey().toString()))
                        .filter(type -> type.getKey().equalsIgnoreCase(keyNode.getKey().toString()))
                        .findFirst();
                if (opFlag.isEmpty()) {
                    ZonePlugin
                            .getZonesPlugin()
                            .getLogger()
                            .error("Could not load flag: Unknown flag Id of '" +
                                    flagPluginNode.getKey().toString() +
                                    ":" +
                                    keyNode.getValue().key() +
                                    "'");
                    continue;
                }
                if (types.containsKey(opFlag.get())) {
                    throw new IllegalStateException("Two or more flag keys found to be the same: " +
                            opFlag.get());
                }
                types.put(opFlag.get(), keyNode.getValue());
            }
        }
        for (Map.Entry<FlagType<?>, ConfigurationNode> entry : types.entrySet()) {
            if (!(entry.getKey() instanceof FlagType.SerializableType)) {
                continue;
            }
            try {
                Flag flag = ((Serializable<? extends Flag>) entry.getKey()).load(entry.getValue());
                builder.addFlags(flag);
            } catch (IOException e) {
                ZonePlugin
                        .getZonesPlugin()
                        .getLogger()
                        .error("Could not load flag: " + e.getMessage());
            }

        }
        return builder.build();
    }

    /**
     * saves the zone into the correct file
     *
     * @param zone The zone to save
     *
     * @return The file that was saved to
     *
     * @throws ConfigurateException if fails to save
     */
    public synchronized File save(Zone zone) throws ConfigurateException {
        File file = new File("config/zone/zones/" +
                zone.getPlugin().metadata().id() +
                "/" +
                zone.getKey() +
                ".conf");
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder().file(file).build();
        ConfigurationNode node = loader.createNode();

        node.node(NAME).set(zone.getName());
        if (zone.getParent().isPresent()) {
            node.node(PARENT).set(zone.getParent().get().getId());
        }
        for (Flag flag : zone.getFlags()) {
            if (!(flag instanceof Flag.Serializable)) {
                continue;
            }
            ConfigurationNode flagNode = node
                    .node(FLAGS)
                    .node(flag.getType().getPlugin().metadata().id())
                    .node(flag.getType().getKey());
            try {
                ((Flag.Serializable) flag).save(flagNode);
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        }
        Optional<ResourceKey> opWorld = zone.getWorldKey();
        if (opWorld.isPresent()) {
            node.node(WORLD).set(opWorld.get().asString());
        }
        Region region = zone.getRegion();
        region.save(node.node(REGION));
        loader.save(node);
        return file;
    }

    public void zonesReload() {
        this.zones.clear();
        File zonesFolder = new File("config/zone/zones/");
        Sponge.systemSubject().sendMessage(Messages.getZonesLoadingFrom(zonesFolder.getPath()));

        for (PluginContainer container : Sponge.pluginManager().plugins()) {
            File keyFolder = new File(zonesFolder, container.metadata().id());
            File[] keyFiles = keyFolder.listFiles();
            if (keyFiles == null) {
                continue;
            }
            for (File file : keyFiles) {
                try {
                    Zone zone = this.load(file);
                    this.register(zone);
                } catch (ConfigurateException e) {
                    Sponge
                            .systemSubject()
                            .sendMessage(Messages.getZonesLoadingFail(file.getPath()));
                    e.printStackTrace();
                }
            }
        }
    }
}
