package command.region.flag.message.greetings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
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
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.DefaultGroups;
import tools.CommandAssert;

import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        this.flag = new GreetingsFlag(Component.text("Test message"),
                MessageDisplayTypes.CHAT.createCopyOfDefault());
        this.zoneManager = new ZoneManager();


        this.plugin = Mockito.mock(ZonePlugin.class);
        this.container = Mockito.mock(PluginContainer.class);
        this.metadata = Mockito.mock(PluginMetadata.class);
        this.version = Mockito.mock(ArtifactVersion.class);
        ZoneConfig config = Mockito.mock(ZoneConfig.class);


        Mockito.when(config.getOrElse(ZoneNodes.MAX_OWNER)).thenReturn(99);
        try {
            Mockito
                    .mockStatic(ZonePlugin.class)
                    .when(ZonePlugin::getZonesPlugin)
                    .thenReturn(this.plugin);
        } catch (MockitoException e) {
            this.plugin = ZonePlugin.getZonesPlugin();
        }
        Mockito.when(this.plugin.getPluginContainer()).thenReturn(this.container);
        Mockito.when(this.plugin.getConfig()).thenReturn(config);
        Mockito.when(this.container.metadata()).thenReturn(this.metadata);
        Mockito.when(this.metadata.id()).thenReturn("zones");

        this.zone = Mockito.mock(Zone.class);


        Mockito.when(this.zone.getId()).thenReturn("zones:test");
        Mockito.when(this.zone.getParentId()).thenReturn(Optional.empty());
        Mockito.when(this.zone.getFlag(FlagTypes.GREETINGS)).thenReturn(Optional.of(this.flag));

        this.zoneManager.register(this.zone);

    }

    @Test
    public void testSetOnServerWithMultiArgumentComponent() {
        //setup
        MembersFlag members = new MembersFlag();

        ServerPlayer player = Mockito.mock(ServerPlayer.class);
        UUID uuid = UUID.randomUUID();
        members.addMember(DefaultGroups.OWNER, uuid);
        Mockito.when(player.uniqueId()).thenReturn(uuid);
        Mockito.when(this.zone.getMembers()).thenReturn(members);


        CommandResult commandResult = Mockito.mock(CommandResult.class);
        try {
            Mockito
                    .mockStatic(CommandResult.class)
                    .when(CommandResult::success)
                    .thenReturn(commandResult);
        } catch (MockitoException e) {
            commandResult = CommandResult.success();
        }


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
        Component greetingsResult = this.flag.getGreetingsMessage();

        //compare
        Assertions.assertEquals(commandResult, result);
        Assertions.assertEquals("test two",
                PlainTextComponentSerializer.plainText().serialize(greetingsResult));
    }

    @Test
    public void testSetOnServer() {
        //setup
        MembersFlag members = new MembersFlag();

        ServerPlayer player = Mockito.mock(ServerPlayer.class);
        UUID uuid = UUID.randomUUID();
        members.addMember(DefaultGroups.OWNER, uuid);
        Mockito.when(player.uniqueId()).thenReturn(uuid);
        Mockito.when(this.zone.getMembers()).thenReturn(members);


        CommandResult commandResult = Mockito.mock(CommandResult.class);
        try {
            Mockito
                    .mockStatic(CommandResult.class)
                    .when(CommandResult::success)
                    .thenReturn(commandResult);
        } catch (MockitoException e) {
            commandResult = CommandResult.success();
        }


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
        Component greetingsResult = this.flag.getGreetingsMessage();

        //compare
        Assertions.assertEquals(commandResult, result);
        Assertions.assertEquals("test",
                PlainTextComponentSerializer.plainText().serialize(greetingsResult));
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
