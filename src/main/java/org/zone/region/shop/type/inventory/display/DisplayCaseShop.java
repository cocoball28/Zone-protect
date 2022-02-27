package org.zone.region.shop.type.inventory.display;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.client.ClientLocation;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.math.vector.Vector3d;
import org.zone.region.shop.type.ShopTypes;
import org.zone.region.shop.type.inventory.InventoryShop;

public class DisplayCaseShop extends InventoryShop {

    private final Vector3d position;
    private final ResourceKey worldKey;

    public DisplayCaseShop(Component component, ClientLocation location) {
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

    @Override
    public @NotNull Location<?, ?> getLocation() {
        if (this.worldKey == null) {
            if (Sponge.isClientAvailable()) {
                return Sponge
                        .client()
                        .world()
                        .orElseThrow(() -> new IllegalStateException("Cannot get the world"))
                        .location(this.position);
            }
        }
        return Sponge
                .server()
                .worldManager()
                .world(this.worldKey)
                .orElseThrow(() -> new IllegalStateException("world is not loaded"))
                .location(this.position);
    }

    @Override
    public DisplayCaseShopType getType() {
        return ShopTypes.DISPLAY_CASE;
    }
}
