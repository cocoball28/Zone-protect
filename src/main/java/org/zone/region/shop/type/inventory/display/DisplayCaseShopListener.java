package org.zone.region.shop.type.inventory.display;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.menu.InventoryMenu;
import org.spongepowered.api.world.LocatableSnapshot;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.math.vector.Vector3i;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.eco.shop.ShopsFlag;
import org.zone.region.shop.Shop;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class DisplayCaseShopListener {

    @Listener
    public void createDisplayItem(MoveEntityEvent event, @Getter("entity") Player player) {
        Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getNearestZoneInView(player);
        if (opZone.isEmpty()) {
            return;
        }
        Zone zone = opZone.get();
        Collection<Shop> shops = zone
                .getFlag(FlagTypes.SHOPS)
                .map(ShopsFlag::getShops)
                .orElse(Collections.emptyList());
        Vector3i playerLoc = player.blockPosition();
        int distance = player.get(Keys.VIEW_DISTANCE).orElse(10);
        shops
                .stream()
                .filter(shop -> shop instanceof DisplayCaseShop)
                .filter(shop -> shop.getLocation().blockPosition().distanceSquared(playerLoc) <
                        distance)
                .map(shop -> (DisplayCaseShop) shop)
                .filter(shop -> shop.getDisplayEntity().isEmpty())
                .forEach(DisplayCaseShop::getOrCreateDisplayEntity);
    }

    @Listener
    public void itemClick(InteractEntityEvent event, @First ServerPlayer player) {
        Vector3i vector = event.entity().blockPosition().add(0, -1, 0);
        World<?, ?> world = event.entity().world();
        if (!(world instanceof ServerWorld sWorld)) {
            return;
        }
        this.openInventory(player, sWorld.createSnapshot(vector));
    }

    @Listener
    public void blockClick(InteractBlockEvent event, @First ServerPlayer player) {
        this.openInventory(player, event.block());
    }

    private void openInventory(
            @NotNull ServerPlayer player, @NotNull LocatableSnapshot<BlockSnapshot> snapshot) {
        @NotNull Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(player.world(), snapshot.position().toDouble());
        if (opZone.isEmpty()) {
            return;
        }
        Zone zone = opZone.get();
        Optional<ShopsFlag> opFlag = zone.getFlag(FlagTypes.SHOPS);
        if (opFlag.isEmpty()) {
            return;
        }
        ShopsFlag flag = opFlag.get();
        Optional<DisplayCaseShop> opShop = flag
                .getShops()
                .stream()
                .filter(shop -> shop.getLocation().blockPosition().equals(snapshot.position()))
                .filter(shop -> shop instanceof DisplayCaseShop)
                .map(shop -> (DisplayCaseShop) shop)
                .findAny();
        if (opShop.isEmpty()) {
            return;
        }
        DisplayCaseShop shop = opShop.get();
        InventoryMenu menu = shop.createInventory(UUID.randomUUID());
        menu.open(player);
    }
}
