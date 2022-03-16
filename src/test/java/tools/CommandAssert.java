package tools;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.service.ServiceProvider;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.metadata.PluginMetadata;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.context.CommandContext;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CommandAssert {

    public static class MockCommandBuilder {

        public final PluginContainer container;
        public final PluginMetadata metadata;
        public final ArtifactVersion version;
        public final CommandContext context;
        public final CommandCause cause;
        public final ZonePlugin plugin;
        public final ServiceProvider.GameScoped serviceProvider;

        public MockCommandBuilder(
                PluginContainer container,
                PluginMetadata metadata,
                ArtifactVersion version,
                CommandContext context,
                CommandCause cause,
                ZonePlugin plugin,
                ServiceProvider.GameScoped serviceProvider) {
            this.container = container;
            this.metadata = metadata;
            this.version = version;
            this.context = context;
            this.cause = cause;
            this.plugin = plugin;
            this.serviceProvider = serviceProvider;
        }
    }

    public static CommandResult test(
            PluginContainer container,
            PluginMetadata metadata,
            ArtifactVersion version,
            CommandContext context,
            CommandCause cause,
            ZonePlugin plugin,
            ServiceProvider.GameScoped serviceProvider,
            Consumer<MockCommandBuilder> mock,
            ArgumentCommand command,
            String... arguments) {
        mock.accept(new MockCommandBuilder(container,
                metadata,
                version,
                context,
                cause,
                plugin,
                serviceProvider));
        return command.run(context, arguments);
    }

    public static CommandResult test(
            Consumer<MockCommandBuilder> mock, ArgumentCommand command, String... arguments) {
        PluginContainer pluginContainer = Mockito.mock(PluginContainer.class);
        PluginMetadata pluginMetadata = Mockito.mock(PluginMetadata.class);
        ArtifactVersion pluginVersion = Mockito.mock(ArtifactVersion.class);
        CommandCause commandCause = Mockito.mock(CommandCause.class);
        ServiceProvider.GameScoped serviceProvider = Mockito.mock(ServiceProvider.GameScoped.class);

        CommandContext context = new CommandContext(commandCause, List.of(command), arguments);


        ZonePlugin plugin = Mockito.mock(ZonePlugin.class);

        Mockito.when(pluginContainer.metadata()).thenReturn(pluginMetadata);
        Mockito.when(pluginMetadata.name()).thenReturn(Optional.of("Zones"));
        Mockito.when(pluginMetadata.version()).thenReturn(pluginVersion);
        Mockito.when(plugin.getPluginContainer()).thenReturn(pluginContainer);

        MockedStatic<ZonePlugin> staticPlugin = Mockito.mockStatic(ZonePlugin.class);
        staticPlugin.when(ZonePlugin::getZonesPlugin).thenReturn(plugin);

        MockedStatic<Sponge> staticSponge = Mockito.mockStatic(Sponge.class);
        staticSponge.when(Sponge::serviceProvider).thenReturn(serviceProvider);

        return test(pluginContainer,
                pluginMetadata,
                pluginVersion,
                context,
                commandCause,
                plugin,
                serviceProvider,
                mock,
                command,
                arguments);
    }

    public static CommandResult test(
            PluginContainer container,
            PluginMetadata metadata,
            ArtifactVersion version,
            ZonePlugin plugin,
            Consumer<MockCommandBuilder> mock,
            ArgumentCommand command,
            String... arguments) {
        CommandCause commandCause = Mockito.mock(CommandCause.class);
        ServiceProvider.GameScoped serviceProvider = Mockito.mock(ServiceProvider.GameScoped.class);

        CommandContext context = new CommandContext(commandCause, List.of(command), arguments);

        MockedStatic<Sponge> staticSponge = Mockito.mockStatic(Sponge.class);
        staticSponge.when(Sponge::serviceProvider).thenReturn(serviceProvider);

        return test(container,
                metadata,
                version,
                context,
                commandCause,
                plugin,
                serviceProvider,
                mock,
                command,
                arguments);
    }
}
