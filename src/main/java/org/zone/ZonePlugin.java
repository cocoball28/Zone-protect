package org.zone;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.zone.commands.structure.ZoneCommands;
import org.zone.event.listener.PlayerListener;
import org.zone.memory.MemoryHolder;
import org.zone.region.Zone;
import org.zone.region.ZoneManager;
import org.zone.region.flag.FlagManager;
import org.zone.region.flag.interact.block.destroy.BlockBreakListener;
import org.zone.region.flag.interact.door.DoorInteractListener;
import org.zone.region.flag.move.monster.MonsterPreventionListener;

import java.io.File;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some common event registrations are included as a jumping-off point.</p>
 */
@Plugin("zones")
public class ZonePlugin {

    private final PluginContainer plugin;
    private final Logger logger;
    private FlagManager flagManager;
    private ZoneManager zoneManager;
    private MemoryHolder memoryHolder;
    private static ZonePlugin zonePlugin;

    public FlagManager getFlagManager() {
        return this.flagManager;
    }

    public ZoneManager getZoneManager() {
        return this.zoneManager;
    }

    public MemoryHolder getMemoryHolder() {
        return this.memoryHolder;
    }

    public Logger getLogger() {
        return this.logger;
    }

    @Inject
    public ZonePlugin(final PluginContainer plugin, final Logger logger) {
        zonePlugin = this;
        this.plugin = plugin;
        this.logger = logger;
    }

    @Listener
    public void onConstructor(ConstructPluginEvent event) {
        this.flagManager = new FlagManager();
        this.zoneManager = new ZoneManager();
        this.memoryHolder = new MemoryHolder();
    }

    private void registerListeners() {
        Sponge.eventManager().registerListeners(this.plugin, new PlayerListener());
        Sponge.eventManager().registerListeners(this.plugin, new MonsterPreventionListener());
        Sponge.eventManager().registerListeners(this.plugin, new DoorInteractListener());
        Sponge.eventManager().registerListeners(this.plugin, new BlockBreakListener());
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        this.registerListeners();
        ZoneManager manager = this.getZoneManager();
        File zonesFolder = new File("config/zone/zones/");
        for (PluginContainer container : Sponge.pluginManager().plugins()) {
            File keyFolder = new File(zonesFolder, container.metadata().id());
            File[] keyFiles = keyFolder.listFiles();
            if (keyFiles == null) {
                continue;
            }
            for (File file : keyFiles) {
                try {
                    Zone zone = manager.load(file);
                    manager.register(zone);
                } catch (ConfigurateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Listener
    public void onRegisterCommands(@SuppressWarnings("BoundedWildcard") final RegisterCommandEvent<Command.Raw> event) {
        event.register(this.plugin, ZoneCommands.createCommand(), "zone", "region", "claim", "protect");
    }

    public PluginContainer getPluginContainer() {
        return this.plugin;
    }

    public static ZonePlugin getZonesPlugin() {
        return zonePlugin;
    }
}
