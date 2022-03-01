package org.zone.region.shop;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.world.Location;
import org.zone.region.shop.selling.Selling;
import org.zone.region.shop.type.ShopType;

import java.util.Collection;

public interface Shop {

    @NotNull Location<?, ?> getLocation();

    ShopType<?> getType();

    @NotNull Collection<? extends Selling<?, ?>> getSelling();

    @NotNull TransactionResult buy(
            @NotNull Player account, @NotNull Selling<?, ?> item, int amount);

    default TransactionResult buy(
            @NotNull Player account, @NotNull Selling<?, ?> item) {
        return this.buy(account, item, 1);
    }


}
