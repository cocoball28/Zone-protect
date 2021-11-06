package org.zone;

import org.spongepowered.api.Server;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some common event registrations are included as a jumping-off point.</p>
 */
@Plugin("zones")
public class ZonePlugin {

    private PluginContainer container;
    private static ZonePlugin plugin;

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        plugin = this;
        this.container = event.plugin();
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {

    }

    public PluginContainer getContainer(){
        return this.container;
    }

    public static ZonePlugin getInstance(){
        return plugin;
    }
}
