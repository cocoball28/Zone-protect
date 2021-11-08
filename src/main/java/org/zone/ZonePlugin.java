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
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.zone.command.ZoneCommands;
import org.zone.event.listener.EntityListener;
import org.zone.event.listener.PlayerListener;
import org.zone.memory.MemoryHolder;
import org.zone.region.ZoneManager;
import org.zone.region.flag.FlagManager;

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

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        Sponge.eventManager().registerListeners(this.plugin, new PlayerListener());
        Sponge.eventManager().registerListeners(this.plugin, new EntityListener());
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        event.register(this.plugin, ZoneCommands.createZoneCommand(), "zone", "region", "claim", "protect");
    }

    public PluginContainer getPluginContainer() {
        return this.plugin;
    }

    public static ZonePlugin getZonesPlugin() {
        return zonePlugin;
    }
}
