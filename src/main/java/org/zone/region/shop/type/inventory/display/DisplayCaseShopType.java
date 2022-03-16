package org.zone.region.shop.type.inventory.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.shop.selling.SellingItemStack;
import org.zone.region.shop.transaction.price.PriceBuilder;
import org.zone.region.shop.transaction.price.PriceType;
import org.zone.region.shop.transaction.price.player.PlayerEcoPrice;
import org.zone.region.shop.type.ShopType;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class DisplayCaseShopType implements ShopType<DisplayCaseShop> {
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
    public @NotNull DisplayCaseShop load(@NotNull ConfigurationNode node) throws IOException {
        if (!node.isList()) {
            throw new IOException("Node is not a list");
        }
        double x = node.node("x").getDouble();
        double y = node.node("y").getDouble();
        double z = node.node("z").getDouble();
        String worldName = node.node("world").getString();
        ResourceKey worldKey = worldName == null ? null : ResourceKey.resolve(worldName);
        String gsonName = node.node("name").getString();
        Component name = gsonName == null ? null : GsonComponentSerializer
                .gson()
                .deserialize(gsonName);

        Set<SellingItemStack> items = node.node("items").childrenList().stream().map(item -> {
            try {
                ItemStackSnapshot stack = item.node("item").get(ItemStackSnapshot.class);
                if (stack == null) {
                    throw new IllegalStateException("No itemstack found");
                }
                int amount = item.node("quantity").getInt();
                String currencyString = item.node("currency").getString();
                double price = item.node("price").getDouble();
                Currency currency = RegistryTypes.CURRENCY
                        .get()
                        .findValue(ResourceKey.resolve(currencyString))
                        .orElseThrow(() -> {
                            throw new IllegalStateException("currency not found with the id of " +
                                    currencyString);
                        });
                PlayerEcoPrice playerPrice = (PlayerEcoPrice) new PriceBuilder()
                        .setType(PriceType.ECO)
                        .setAmount(price)
                        .setCurrency(currency)
                        .buildPlayer();
                return new SellingItemStack(playerPrice, stack, amount);
            } catch (SerializationException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        }).collect(Collectors.toSet());

        DisplayCaseShop shop = new DisplayCaseShop(name, worldKey, new Vector3d(x, y, z));
        shop.register(items);
        return shop;
    }

    @Override
    public void save(
            @NotNull ConfigurationNode node, @Nullable DisplayCaseShop save) throws IOException {
        if (save == null) {
            return;
        }
        Location<?, ?> loc = save.getLocation();
        node.node("x").set(loc.x());
        node.node("y").set(loc.y());
        node.node("z").set(loc.z());
        if (loc instanceof ServerLocation sLoc) {
            node.node("world").set(sLoc.world().key().formatted());
        }
        node.node("name").set(GsonComponentSerializer.gson().serialize(save.getName()));

        save.getSellingItems().forEach(selling -> {
            ConfigurationNode configNode = node.node("items").appendListNode();
            try {
                configNode.node("item").set(ItemStackSnapshot.class, selling.getItem());
                configNode.node("quantity").set(selling.getAmount());
                configNode
                        .node("currency")
                        .set(selling.getPrice().getCurrency().key(RegistryTypes.CURRENCY));
                configNode.node("price").set(selling.getPrice().getAmount().doubleValue());
            } catch (SerializationException e) {
                e.printStackTrace();
            }
        });
    }
}
