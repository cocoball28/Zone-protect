package command.region.flag.message.greetings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.metadata.PluginMetadata;
import org.zone.ZonePlugin;
import org.zone.commands.structure.ZoneCommands;
import org.zone.config.ZoneConfig;
import org.zone.config.node.ZoneNodes;
import org.zone.region.Zone;
import org.zone.region.ZoneManager;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.DefaultGroups;
import tools.CommandAssert;

import java.util.Optional;
import java.util.UUID;

public class TestGreetingsSetCommand {

    private ZoneManager zoneManager;
    private GreetingsFlag flag;
    private ZonePlugin plugin;
    private PluginContainer container;
    private PluginMetadata metadata;
    private ArtifactVersion version;
    private Zone zone;

    @BeforeAll
    void init() {
        flag = new GreetingsFlag(Component.text("Test message"));
        zoneManager = new ZoneManager();


        plugin = Mockito.mock(ZonePlugin.class);
        container = Mockito.mock(PluginContainer.class);
        metadata = Mockito.mock(PluginMetadata.class);
        version = Mockito.mock(ArtifactVersion.class);
        ZoneConfig config = Mockito.mock(ZoneConfig.class);


        Mockito.when(config.getOrElse(ZoneNodes.MAX_OWNER)).thenReturn(99);
        Mockito.mockStatic(ZonePlugin.class).when(ZonePlugin::getZonesPlugin).thenReturn(plugin);
        Mockito.when(plugin.getPluginContainer()).thenReturn(container);
        Mockito.when(plugin.getConfig()).thenReturn(config);
        Mockito.when(container.metadata()).thenReturn(metadata);
        Mockito.when(metadata.id()).thenReturn("zones");

        zone = Mockito.mock(Zone.class);


        Mockito.when(zone.getId()).thenReturn("zones:test");
        Mockito.when(zone.getParentId()).thenReturn(Optional.empty());
        Mockito.when(zone.getFlag(FlagTypes.GREETINGS)).thenReturn(Optional.of(this.flag));

        zoneManager.register(zone);

    }

    @Test
    public void testSetOnServerWithMultiArgumentComponent() {
        //setup
        MembersFlag members = new MembersFlag();

        ServerPlayer player = Mockito.mock(ServerPlayer.class);
        UUID uuid = UUID.randomUUID();
        members.addMember(DefaultGroups.OWNER, uuid);
        Mockito.when(player.uniqueId()).thenReturn(uuid);
        Mockito.when(zone.getMembers()).thenReturn(members);


        CommandResult commandResult = Mockito.mock(CommandResult.class);
        Mockito
                .mockStatic(CommandResult.class)
                .when(CommandResult::success)
                .thenReturn(commandResult);


        //test
        CommandResult result = CommandAssert.test(this.container,
                this.metadata,
                this.version,
                this.plugin,
                mcb -> {
                    Mockito.when(mcb.plugin.getZoneManager()).thenReturn(this.zoneManager);
                    Mockito.when(mcb.cause.subject()).thenReturn(player);
                },
                ZoneCommands.ZONE_FLAG_GREETINGS_MESSAGE_COMMAND,
                "region",
                "flag",
                "zones:test",
                "greetings",
                "message",
                "set",
                "test",
                "two");
        Optional<Component> opResult = flag.getMessage();

        //compare
        Assertions.assertEquals(commandResult, result);
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals("test",
                PlainTextComponentSerializer.plainText().serialize(opResult.get()));
    }

    @Test
    public void testSetOnServer() {
        //setup
        MembersFlag members = new MembersFlag();

        ServerPlayer player = Mockito.mock(ServerPlayer.class);
        UUID uuid = UUID.randomUUID();
        members.addMember(DefaultGroups.OWNER, uuid);
        Mockito.when(player.uniqueId()).thenReturn(uuid);
        Mockito.when(zone.getMembers()).thenReturn(members);


        CommandResult commandResult = Mockito.mock(CommandResult.class);
        Mockito
                .mockStatic(CommandResult.class)
                .when(CommandResult::success)
                .thenReturn(commandResult);


        //test
        CommandResult result = CommandAssert.test(this.container,
                this.metadata,
                this.version,
                this.plugin,
                mcb -> {
                    Mockito.when(mcb.plugin.getZoneManager()).thenReturn(this.zoneManager);
                    Mockito.when(mcb.cause.subject()).thenReturn(player);
                },
                ZoneCommands.ZONE_FLAG_GREETINGS_MESSAGE_COMMAND,
                "region",
                "flag",
                "zones:test",
                "greetings",
                "message",
                "set",
                "test");
        Optional<Component> opResult = flag.getMessage();

        //compare
        Assertions.assertEquals(commandResult, result);
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals("test",
                PlainTextComponentSerializer.plainText().serialize(opResult.get()));
    }

    //should be checking audience, not subject
    /*@Test
    public void testSetOnClient() {
        //setup
        MembersFlag members = new MembersFlag();

        LocalPlayer player = Mockito.mock(LocalPlayer.class);
        UUID uuid = UUID.randomUUID();
        members.addMember(DefaultGroups.OWNER, uuid);
        Mockito.when(player.uniqueId()).thenReturn(uuid);
        Mockito.when(zone.getMembers()).thenReturn(members);


        CommandResult commandResult = Mockito.mock(CommandResult.class);
        Mockito
                .mockStatic(CommandResult.class)
                .when(CommandResult::success)
                .thenReturn(commandResult);


        //test
        CommandResult result = CommandAssert.test(this.container,
                this.metadata,
                this.version,
                this.plugin,
                mcb -> {
                    Mockito.when(mcb.plugin.getZoneManager()).thenReturn(this.zoneManager);
                    Mockito.when(mcb.cause.subject()).thenReturn(player);
                },
                ZoneCommands.ZONE_FLAG_GREETINGS_MESSAGE_COMMAND,
                "region",
                "flag",
                "zones:test",
                "greetings",
                "message",
                "set",
                "test");
        Optional<Component> opResult = flag.getMessage();

        //compare
        Assertions.assertEquals(commandResult, result);
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals("test",
                PlainTextComponentSerializer.plainText().serialize(opResult.get()));
    }*/
}
