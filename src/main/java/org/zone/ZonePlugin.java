package org.zone;

import com.google.inject.Inject;
import net.kyori.adventure.audience.Audience;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.zone.ai.HumanAIListener;
import org.zone.annotations.Typed;
import org.zone.commands.structure.ZoneCommands;
import org.zone.config.ZoneConfig;
import org.zone.event.listener.PlayerListener;
import org.zone.keys.ZoneKeys;
import org.zone.memory.MemoryHolder;
import org.zone.region.Zone;
import org.zone.region.ZoneManager;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagManager;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.entity.monster.block.explode.creeper.CreeperGriefListener;
import org.zone.region.flag.entity.monster.block.explode.enderdragon.EnderDragonGriefListener;
import org.zone.region.flag.entity.monster.block.explode.wither.WitherGriefListener;
import org.zone.region.flag.entity.monster.block.hatch.EnderMiteGriefListener;
import org.zone.region.flag.entity.monster.block.ignite.SkeletonGriefListener;
import org.zone.region.flag.entity.monster.block.knock.ZombieGriefListener;
import org.zone.region.flag.entity.monster.block.take.EnderManGriefListener;
import org.zone.region.flag.entity.monster.move.MonsterPreventionListener;
import org.zone.region.flag.entity.nonliving.block.farmland.FarmTramplingListener;
import org.zone.region.flag.entity.nonliving.block.tnt.TnTDefuseListener;
import org.zone.region.flag.entity.player.damage.attack.EntityDamagePlayerListener;
import org.zone.region.flag.entity.player.damage.fall.PlayerFallDamageListener;
import org.zone.region.flag.entity.player.interact.block.destroy.BlockBreakListener;
import org.zone.region.flag.entity.player.interact.block.place.BlockPlaceListener;
import org.zone.region.flag.entity.player.interact.door.DoorInteractListener;
import org.zone.region.flag.entity.player.interact.itemframe.ItemFrameInteractionListener;
import org.zone.region.flag.entity.player.display.MessageDisplayManager;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlagListener;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlagListener;
import org.zone.region.flag.entity.player.move.preventing.PreventPlayersListener;
import org.zone.region.group.key.GroupKeyManager;
import org.zone.region.shop.type.ShopManager;
import org.zone.region.shop.type.inventory.display.DisplayCaseShopListener;
import org.zone.utils.Messages;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The zone plugin's boot and main class, use {@link ZonePlugin#getZonesPlugin()} to gain an
 * instance of this class
 * @since 1.0.0
 */
@Plugin("zones")
public class ZonePlugin {

    /**
     * Plugin container of this plugin
     */
    private final PluginContainer plugin;
    /**
     * Logger of this plugin
     */
    private final Logger logger;
    /**
     * Message Display Manager of this plugin
     */
    private MessageDisplayManager messageDisplayManager;
    /**
     * Flag Manager of this plugin
     */
    private FlagManager flagManager;
    /**
     * Zone manager of this plugin
     */
    private ZoneManager zoneManager;
    /**
     * Group key manager of this plugin
     */
    private GroupKeyManager groupKeyManager;
    /**
     * Shops manager of this plugin
     */
    private ShopManager shopManager;
    /**
     * Config of this plugin
     */
    private ZoneConfig config;
    /**
     * Memory Holder of this plugin
     */
    private MemoryHolder memoryHolder;
    /**
     * This plugin
     */
    private static ZonePlugin zonePlugin;

    @SuppressWarnings("SpongeInjection")
    @Inject
    public ZonePlugin(final PluginContainer plugin, final Logger logger) {
        zonePlugin = this;
        this.plugin = plugin;
        this.logger = logger;
    }

    /**
     * Gets the config of {@link ZonePlugin}
     *
     * @return An instance of the config class
     * @since 1.0.1
     * @see ZoneConfig
     */
    public @NotNull ZoneConfig getConfig() {
        return this.config;
    }

    /**
     * Gets the shop manager of {@link ZonePlugin}
     *
     * @return An instance of the shop manager
     * @since 1.0.1
     * @see ShopManager
     */
    public @NotNull ShopManager getShopManager() {
        return this.shopManager;
    }

    /**
     * Gets the message display manager
     *
     * @return The instance of the message display manager
     * @since 1.0.1
     * @see MessageDisplayManager
     */
    public @NotNull MessageDisplayManager getMessageDisplayManager() {
        return this.messageDisplayManager;
    }

    /**
     * Gets the flag manager
     *
     * @return The instance of the flag manager
     * @since 1.0.0
     * @see FlagManager
     */
    public @NotNull FlagManager getFlagManager() {
        return this.flagManager;
    }

    /**
     * Gets the zone manager
     *
     * @return The instance of the zone manager
     * @see 1.0.0
     * @see ZoneManager
     */
    public @NotNull ZoneManager getZoneManager() {
        return this.zoneManager;
    }

    /**
     * Gets the Memory holder
     *
     * @return The instance of the memory holder
     * @see MemoryHolder
     */
    public @NotNull MemoryHolder getMemoryHolder() {
        return this.memoryHolder;
    }

    /**
     * Gets the Group key manager
     *
     * @return The instance of the group key manager
     * @since 1.0.0
     * @see GroupKeyManager
     */
    public @NotNull GroupKeyManager getGroupKeyManager() {
        return this.groupKeyManager;
    }

    /**
     * Gets the logger for this plugin (Oh no! log4j!)
     *
     * @return The logger for this plugin
     * @since 1.0.0
     */
    public @NotNull Logger getLogger() {
        return this.logger;
    }

    /**
     * Listener of Construct plugin event
     *
     * Mainly initializes fields available in this plugin class by creating a new instance of the
     * field type
     *
     * @param event The event to listen to. Here, {@link ConstructPluginEvent}
     * @since 1.0.0
     */
    @Listener
    public void onConstruct(ConstructPluginEvent event) {
        this.messageDisplayManager = new MessageDisplayManager();
        this.flagManager = new FlagManager();
        this.zoneManager = new ZoneManager();
        this.groupKeyManager = new GroupKeyManager();
        this.memoryHolder = new MemoryHolder();
        this.shopManager = new ShopManager();
        this.config = new ZoneConfig(new File("config/zone/config.conf"));
    }

    /**
     * Method where listeners of events are registered (Only listeners of flags)
     *
     * @since 1.0.0
     */
    private void registerListeners() {
        EventManager eventManager = Sponge.eventManager();
        eventManager.registerListeners(this.plugin, new PlayerListener());
        eventManager.registerListeners(this.plugin, new MonsterPreventionListener());
        eventManager.registerListeners(this.plugin, new DoorInteractListener());
        eventManager.registerListeners(this.plugin, new BlockBreakListener());
        eventManager.registerListeners(this.plugin, new BlockPlaceListener());
        eventManager.registerListeners(this.plugin, new GreetingsFlagListener());
        eventManager.registerListeners(this.plugin, new PreventPlayersListener());
        eventManager.registerListeners(this.plugin, new LeavingFlagListener());
        eventManager.registerListeners(this.plugin, new ItemFrameInteractionListener());
        eventManager.registerListeners(this.plugin, new EntityDamagePlayerListener());
        eventManager.registerListeners(this.plugin, new PlayerFallDamageListener());
        eventManager.registerListeners(this.plugin, new TnTDefuseListener());
        eventManager.registerListeners(this.plugin, new FarmTramplingListener());
        eventManager.registerListeners(this.plugin, new CreeperGriefListener());
        eventManager.registerListeners(this.plugin, new EnderManGriefListener());
        eventManager.registerListeners(this.plugin, new ZombieGriefListener());
        eventManager.registerListeners(this.plugin, new SkeletonGriefListener());
        eventManager.registerListeners(this.plugin, new EnderDragonGriefListener());
        eventManager.registerListeners(this.plugin, new WitherGriefListener());
        eventManager.registerListeners(this.plugin, new EnderMiteGriefListener());
        eventManager.registerListeners(this.plugin, new HumanAIListener());
        eventManager.registerListeners(this.plugin, new DisplayCaseShopListener());
    }

    /**
     * Listener of Starting Engine Event
     *
     * @param event The event to listen to. Here, {@link StartedEngineEvent<Server>}
     * @since 1.0.0
     */
    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        this.registerListeners();
    }

    /**
     * Listener of Started Engine Event
     *
     * @param event The event to listen to. Here, {@link StartedEngineEvent<Server>}
     * @since 1.0.0
     */
    @Listener
    public void onServerStarted(final StartedEngineEvent<Server> event) {
        this.config.loadDefaults();


        FlagManager manager = this.getFlagManager();
        //noinspection unchecked
        Iterable<? extends FlagType.SerializableType<?>> types = (Iterable<? extends FlagType.SerializableType<?>>) manager
                .getRegistered(FlagType.SerializableType.class)
                .collect(Collectors.toSet());
        for (FlagType.SerializableType<?> type : types) {
            if (type instanceof FlagType.TaggedFlagType) {
                continue;
            }
            Optional<?> opDefault = manager.getDefaultFlags().loadDefault(type);
            if (opDefault.isPresent()) {
                continue;
            }
            Optional<? extends Flag.Serializable> opFlag = type.createCopyOfDefaultFlag();
            if (opFlag.isEmpty()) {
                continue;
            }
            try {
                manager.getDefaultFlags().setDefault(opFlag.get());
            } catch (IOException e) {
                this
                        .getLogger()
                        .error("Could not set the defaults for '" +
                                type.getId() +
                                "' " +
                                "despite a copy of default found. Is saving done " +
                                "correctly?");
                e.printStackTrace();
            }
        }
        try {
            manager.getDefaultFlags().save();
        } catch (ConfigurateException e) {
            this.getLogger().error("Could not save defaults file");
            e.printStackTrace();
        }

        Sponge.systemSubject().sendMessage(Messages.getLoadingZonesStart());
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
                    Zone zone = this.zoneManager.load(file);
                    this.zoneManager.register(zone);
                } catch (ConfigurateException e) {
                    Sponge
                            .systemSubject()
                            .sendMessage(Messages.getZonesLoadingFail(file.getPath()));
                    e.printStackTrace();
                }
            }
        }
        Sponge
                .systemSubject()
                .sendMessage(Messages.getZonesLoaded(this.getZoneManager().getRegistered()));
    }

    /**
     * Listener of RegisterCommandEvent
     *
     * @param event The event to listen to. Here, {@link RegisterCommandEvent<Command.Raw>}
     * @since 1.0.0
     */
    @Listener
    public void onRegisterCommands(@SuppressWarnings("BoundedWildcard") final RegisterCommandEvent<Command.Raw> event) {
        event.register(this.plugin,
                ZoneCommands.createCommand(),
                "zone",
                "region",
                "claim",
                "protect");
    }

    /**
     * Listener to RegisterDataEvent
     *
     * @param event The event to listen to. Here, {@link RegisterDataEvent}
     * @since 1.0.1
     */
    @Listener
    public void onRegisterData(RegisterDataEvent event) {
        event.register(DataRegistration.of(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID, Human.class));
    }

    /**
     * Listener to RefreshGameEvent
     *
     * @param event The event to listen to. Here, {@link RefreshGameEvent}
     * @since 1.0.1
     */
    @Listener
    public void onReload(RefreshGameEvent event) {
        Optional<Audience> opCSender = event.cause().first(Audience.class);
        try {
            this.config.getLoader().load();
            opCSender.ifPresent(audience -> audience.sendMessage(Messages.getZoneConfigReloadedInfo()));
            this.zoneManager.reloadZones();
            opCSender.ifPresent(audience -> audience.sendMessage(Messages.getZonesReloadedInfo()));
        } catch (ConfigurateException ce) {
            opCSender.ifPresent(audience -> audience.sendMessage(Messages.getZoneConfigReloadFail()));
            ce.printStackTrace();
            this.logger.error("Event terminated!");
        }
    }

    /**
     * Gets the PluginContainer for this plugin
     *
     * @return The plugin container for this plugin
     * @since 1.0.0
     */
    public @NotNull PluginContainer getPluginContainer() {
        return this.plugin;
    }

    /**
     * Gets the instance of this class
     *
     * @return The instance of this class
     * @since 1.0.0
     */
    public static @NotNull ZonePlugin getZonesPlugin() {
        return zonePlugin;
    }

    /**
     * Gets fields from a type class which extends Identifiable
     *
     * @param type The types class which accepts classes which extend {@link Identifiable}
     *
     * @param <T>  A variable which accepts classes which extends {@link Identifiable}
     *
     * @return The fields from a type class
     * @since 1.0.1
     */
    public <T extends Identifiable> Stream<T> getVanillaTypes(Class<T> type) {
        Typed typedAnnotation = type.getAnnotation(Typed.class);
        if (typedAnnotation == null) {
            throw new IllegalArgumentException("identifiable has no vanilla types");
        }
        Class<?> typesClass = typedAnnotation.typesClass();
        if (Enum.class.isAssignableFrom(typesClass)) {
            //noinspection rawtypes
            Class<? extends Enum> enumTypesClass = (Class<? extends Enum<?>>) typesClass;
            //noinspection unchecked
            return EnumSet.allOf(enumTypesClass).stream();
        }
        return Arrays
                .stream(typesClass.getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> Modifier.isFinal(field.getModifiers()))
                .filter(field -> type.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return (T) field.get(null);
                    } catch (IllegalAccessException e) {
                        //noinspection ReturnOfNull
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }

}
