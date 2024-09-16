package org.zone.region.shop.transaction.price;

import org.zone.region.shop.transaction.price.player.PlayerEcoPrice;
import org.zone.region.shop.transaction.price.player.PlayerExpPrice;
import org.zone.region.shop.transaction.price.player.PlayerLevelPrice;
import org.zone.region.shop.transaction.price.zone.ZoneEcoPrice;
import org.zone.region.shop.transaction.price.zone.ZonePowerPrice;

import java.util.Optional;

/**
 * All known price types
 *
 * @since 1.0.1
 */
public enum PriceType {

    POWER(null, ZonePowerPrice.class),
    ECO(PlayerEcoPrice.class, ZoneEcoPrice.class),
    EXP(PlayerExpPrice.class, null),
    LEVEL(PlayerLevelPrice.class, null);

    private final Class<? extends Price.PlayerPrice<?>> playerClass;
    private final Class<? extends Price.ZonePrice<?>> zoneClass;

    PriceType(
            Class<? extends Price.PlayerPrice<?>> playerClass,
            Class<? extends Price.ZonePrice<?>> zoneClass) {
        this.playerClass = playerClass;
        this.zoneClass = zoneClass;
    }

    /**
     * Gets the player edition class of this price
     *
     * @return The class for the player version of this price
     */
    public Optional<Class<? extends Price.PlayerPrice<?>>> getPlayerClass() {
        return Optional.of(this.playerClass);
    }

    /**
     * Gets the zone edition class of this price
     *
     * @return The class for the zone version of this price
     */
    public Optional<Class<? extends Price.ZonePrice<?>>> getZoneClass() {
        return Optional.of(this.zoneClass);
    }


}
