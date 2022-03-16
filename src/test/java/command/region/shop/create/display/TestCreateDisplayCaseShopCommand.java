package command.region.shop.create.display;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.util.blockray.RayTrace;
import org.spongepowered.api.util.blockray.RayTraceResult;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.api.world.Location;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.metadata.PluginMetadata;
import org.zone.ZonePlugin;
import org.zone.commands.structure.ZoneCommands;
import org.zone.config.ZoneConfig;
import org.zone.config.node.ZoneNodes;
import org.zone.region.Zone;
import org.zone.region.ZoneManager;
import org.zone.region.bounds.BoundedRegion;
import org.zone.region.bounds.ChildRegion;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.eco.shop.ShopsFlag;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.DefaultGroups;
import org.zone.region.group.key.GroupKeys;
import org.zone.region.shop.Shop;
import org.zone.region.shop.type.inventory.display.DisplayCaseShop;
import tools.CommandAssert;
import tools.TestLocatableBlock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCreateDisplayCaseShopCommand {

    private Zone zone;
    private ZoneManager zoneManager;
    private ShopsFlag flag;
    private DisplayCaseShop shop;
    private PluginContainer container;
    private PluginMetadata metadata;
    private ArtifactVersion version;
    private ZonePlugin plugin;

    private Vector3i atPosition = new Vector3i(1, 15, 0);

    @BeforeAll
    void init() {
        flag = new ShopsFlag();
        zoneManager = new ZoneManager();

        plugin = Mockito.mock(ZonePlugin.class);
        container = Mockito.mock(PluginContainer.class);
        metadata = Mockito.mock(PluginMetadata.class);
        version = Mockito.mock(ArtifactVersion.class);
        zone = Mockito.mock(Zone.class);
        ZoneConfig config = Mockito.mock(ZoneConfig.class);
        RayTrace<LocatableBlock> rayTrace = Mockito.mock(RayTrace.class);
        RayTraceResult<LocatableBlock> rayTraceResult = Mockito.mock(RayTraceResult.class);
        Location<?, ?> loc = Mockito.mock(Location.class);
        TestLocatableBlock blockAt = new TestLocatableBlock();
        blockAt.location = loc;
        BoundedRegion region = new BoundedRegion(new Vector3i(0, 0, 0),
                new Vector3i(100, 100, 100));
        ChildRegion childRegion = new ChildRegion(List.of(region));


        Mockito.mockStatic(RayTrace.class).when(RayTrace::block).thenReturn(rayTrace);
        Mockito.mockStatic(ZonePlugin.class).when(ZonePlugin::getZonesPlugin).thenReturn(plugin);

        Mockito.when(zone.getRegion()).thenReturn(childRegion);
        Mockito.when(rayTrace.direction(Mockito.<Living>any())).thenReturn(rayTrace);
        Mockito.when(rayTrace.sourcePosition(Mockito.<Entity>any())).thenReturn(rayTrace);
        Mockito.when(rayTrace.limit(8)).thenReturn(rayTrace);
        Mockito.when(rayTrace.execute()).thenReturn(Optional.of(rayTraceResult));
        Mockito.when(rayTraceResult.selectedObject()).thenReturn(blockAt);
        Mockito.when(config.getOrElse(ZoneNodes.MAX_OWNER)).thenReturn(99);
        Mockito.when(plugin.getPluginContainer()).thenReturn(container);
        Mockito.when(plugin.getConfig()).thenReturn(config);
        Mockito.when(container.metadata()).thenReturn(metadata);
        Mockito.when(metadata.id()).thenReturn("zones");
        Mockito.when(zone.getId()).thenReturn("zones:test");
        Mockito.when(zone.getParentId()).thenReturn(Optional.empty());
        Mockito.when(zone.getFlag(FlagTypes.SHOPS)).thenReturn(Optional.of(this.flag));
        Mockito.when(zone.inRegion(Mockito.any(), Mockito.any())).thenReturn(true);

        zoneManager.register(zone);


    }

    @Test
    public void testSetOnServer() {
        //setup
        MembersFlag members = new MembersFlag();

        ServerPlayer player = Mockito.mock(ServerPlayer.class);
        UUID uuid = UUID.randomUUID();
        members.addMember(DefaultGroups.OWNER, uuid);
        members.addKey(DefaultGroups.OWNER, GroupKeys.CREATE_SHOP);
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
                ZoneCommands.CREATE_DISPLAY_SHOP,
                "region",
                "shop",
                "create",
                "display",
                "test",
                "shop");


        //compare
        Assertions.assertEquals(commandResult, result);
        Assertions.assertEquals(1, flag.getShops().size());
    }
}
