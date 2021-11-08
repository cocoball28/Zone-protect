package org.zone;

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

    private PluginContainer container;
    private final FlagManager flagManager = new FlagManager();
    private final ZoneManager zoneManager = new ZoneManager();
    private final MemoryHolder memoryHolder = new MemoryHolder();
    private static ZonePlugin plugin;

    public FlagManager getFlagManager() {
        return this.flagManager;
    }

    public ZoneManager getZoneManager() {
        return this.zoneManager;
    }

    public MemoryHolder getMemoryHolder() {
        return this.memoryHolder;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        plugin = this;
        this.container = event.plugin();
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        Sponge.eventManager().registerListeners(this.container, new PlayerListener());
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        event.register(this.container, ZoneCommands.createZoneCommand(), "zone", "region", "claim", "protect");
    }

    public PluginContainer getContainer() {
        return this.container;
    }

    public static ZonePlugin getInstance() {
        return plugin;
    }
}
