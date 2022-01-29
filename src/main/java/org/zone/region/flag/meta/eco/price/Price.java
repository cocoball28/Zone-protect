package org.zone.region.flag.meta.eco.price;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;
import org.zone.region.Zone;

import java.math.BigDecimal;

public interface Price<O, N extends Number> {

    interface EcoPrice<O> extends Price<O, BigDecimal> {

        Currency getCurrency();
    }

    interface PlayerPrice<N extends Number> extends Price<Player, N> {

    }

    interface ZonePrice<N extends Number> extends Price<Zone, N> {

    }

    boolean hasEnough(@NotNull O player);

    boolean withdraw(O player);

    float getPercentLeft(@NotNull O player);

    N getAmount();

    PriceType getType();

    @NotNull Component getDisplayName();

    default PriceBuilder asBuilder() {
        return new PriceBuilder().setType(this.getType()).setAmount(this.getAmount().doubleValue());
    }

}
