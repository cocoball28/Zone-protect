package org.zone.region.shop.type.inventory.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.math.vector.Vector3d;
import org.zone.ZonePlugin;
import org.zone.region.shop.selling.SellingItemStack;
import org.zone.region.shop.type.ShopTypes;
import org.zone.region.shop.type.inventory.InventoryShop;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DisplayCaseShop extends InventoryShop {

    private final Vector3d position;
    private final ResourceKey worldKey;

    private static final Function<ItemStackSnapshot, String> TO_NAME = item -> item
            .get(Keys.DISPLAY_NAME)
            .map(comp -> PlainTextComponentSerializer.plainText().serialize(comp))
            .orElse(item.type().key(RegistryTypes.ITEM_TYPE).formatted());

    public DisplayCaseShop(Component component, Location<?, ?> location) {
        this(component, null, location.position());
    }

    public DisplayCaseShop(Component component, ServerLocation location) {
        this(component, location.worldKey(), location.position());
    }

    public DisplayCaseShop(Component component, ResourceKey worldKey, Vector3d position) {
        super(component);
        this.worldKey = worldKey;
        this.position = position;
    }

    private ScheduledTask createDelayRun() {
        Task task = Task
                .builder()
                .plugin(ZonePlugin.getZonesPlugin().getPluginContainer())
                .execute(() -> {
                    try {
                        this.switchDisplayItems();
                        this.createDelayRun();
                    } catch (IllegalStateException ignored) {

                    }
                })
                .delay(Ticks.minecraftHour())
                .build();
        if (Sponge.isServerAvailable()) {
            return Sponge.server().scheduler().submit(task);
        }
        return Sponge.client().scheduler().submit(task);
    }

    public Vector3d getEntityPosition() {
        return this.position.add(0, 0.5, 0);
    }

    public Optional<Item> getDisplayEntity() {
        Vector3d position = this.getEntityPosition();
        return this
                .getWorld()
                .nearbyEntities(position, 0.5)
                .stream()
                .filter(entity -> entity instanceof Item)
                .map(entity -> (Item) entity)
                .findAny();
    }

    public Item getOrCreateDisplayEntity() {
        if (this.getSellingItems().isEmpty()) {
            throw new IllegalStateException("No items to sell");
        }
        Optional<Item> opCurrent = this.getDisplayEntity();
        if (opCurrent.isPresent()) {
            return opCurrent.get();
        }
        World<?, ?> world = this.getWorld();
        Item item = world.createEntity(EntityTypes.ITEM, this.getEntityPosition());
        item.infinitePickupDelay().set(true);
        item.glowing().set(true);
        item.gravityAffected().set(false);
        this.switchDisplayItem(item);
        world.spawnEntity(item);
        this.createDelayRun();
        return item;
    }

    private void switchDisplayItems() {
        Optional<Item> opItem = this.getDisplayEntity();
        if (opItem.isEmpty()) {
            throw new IllegalStateException("Could not find item");
        }
        this.switchDisplayItem(opItem.get());
    }

    private void switchDisplayItem(Item entity) {
        ItemStackSnapshot current = entity.item().get();
        List<ItemStackSnapshot> items = this
                .getSellingItems()
                .stream()
                .map(SellingItemStack::getItem)
                .sorted(Comparator.comparing(TO_NAME))
                .toList();
        if (items.isEmpty()) {
            throw new IllegalStateException("No items to sell");
        }
        int index = items.indexOf(current);
        if (index >= items.size()) {
            index = 0;
        }
        entity.item().set((items.get(index)));
    }

    public World<?, ?> getWorld() {
        if (this.worldKey == null) {
            if (Sponge.isClientAvailable()) {
                return Sponge
                        .client()
                        .world()
                        .orElseThrow(() -> new IllegalStateException("Cannot get the world"));
            }
        }
        return Sponge
                .server()
                .worldManager()
                .world(this.worldKey)
                .orElseThrow(() -> new IllegalStateException("world is not loaded"));
    }

    @Override
    public @NotNull Location<?, ?> getLocation() {
        return this.getWorld().location(this.position);
    }

    @Override
    public DisplayCaseShopType getType() {
        return ShopTypes.DISPLAY_CASE;
    }
}
