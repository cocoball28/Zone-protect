package org.zone.region.shop.transaction.price;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;
import org.zone.region.Zone;

import java.math.BigDecimal;

/**
 * A specified price -> This can be the traditional economy but also other trading options such
 * as players exo
 *
 * @param <O> The accepting price holder -> such as a {@link org.spongepowered.api.service.economy.account.Account}
 * @param <N> The amount type such as int, double, float, etc
 * @since 1.0.1
 */
public interface Price<O, N extends Number> {

    interface EcoPrice<O> extends Price<O, BigDecimal> {

        Currency getCurrency();
    }

    interface PlayerPrice<N extends Number> extends Price<Player, N> {

    }

    interface ZonePrice<N extends Number> extends Price<Zone, N> {

    }

    /**
     * Checks if the price holder has enough
     *
     * @param player The price holder
     *
     * @return True if they have enough, false if they don't
     * @since 1.0.1
     */
    boolean hasEnough(@NotNull O player);

    /**
     * Withdraws the amount from the price holder
     *
     * @param player The price holder
     *
     * @return True if successful, false if unsuccessful
     * @since 1.0.1
     */
    boolean withdraw(O player);

    float getPercentLeft(@NotNull O player);

    /**
     * Gets the amount of the price
     *
     * @return The price amount
     * @since 1.0.1
     */
    @NotNull N getAmount();

    /**
     * The type of the price
     *
     * @return The type of price
     * @since 1.0.1
     */
    @NotNull PriceType getType();

    /**
     * Gets the format in which this should be displayed, such as if this was economy with the
     * currency of 30GBP, then it would return '£30'
     *
     * @return The display format
     * @since 1.0.1
     */
    @NotNull Component getDisplayName();

    /**
     * Returns the price as a builder
     *
     * @return The builder
     * @since 1.0.1
     */
    default @NotNull PriceBuilder asBuilder() {
        return new PriceBuilder().setType(this.getType()).setAmount(this.getAmount().doubleValue());
    }

}
