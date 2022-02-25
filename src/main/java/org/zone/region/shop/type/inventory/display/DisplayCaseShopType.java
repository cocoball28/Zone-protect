package org.zone.region.shop.type.inventory.display;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.shop.Shop;
import org.zone.region.shop.type.ShopType;
import org.zone.region.shop.type.inventory.InventoryShop;

import java.io.IOException;

public class DisplayCaseShopType implements ShopType<InventoryShop> {
    @Override
    public @NotNull String getName() {
        return "Display case";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "display_case";
    }

    @Override
    public @NotNull InventoryShop load(@NotNull ConfigurationNode node) throws IOException {
        return null;
    }

    @Override
    public void save(
            @NotNull ConfigurationNode node, @Nullable InventoryShop save) throws IOException {
if(save == null){
    return;
}
save.getSellingItems()
node.node("").set(save.)
    }
}
