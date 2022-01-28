package org.zone.region.flag.meta.eco.price;

import org.zone.region.flag.meta.eco.price.player.PlayerEcoPrice;
import org.zone.region.flag.meta.eco.price.player.PlayerExpPrice;
import org.zone.region.flag.meta.eco.price.player.PlayerLevelPrice;
import org.zone.region.flag.meta.eco.price.zone.ZoneEcoPrice;
import org.zone.region.flag.meta.eco.price.zone.ZonePowerPrice;

import java.util.Optional;

public enum PriceType {

    POWER(null, ZonePowerPrice.class),
    ECO(PlayerEcoPrice.class, ZoneEcoPrice.class),
    EXP(PlayerExpPrice.class, null),
    LEVEL(PlayerLevelPrice.class, null);

    Class<? extends Price.PlayerPrice<?>> playerClass;
    Class<? extends Price.ZonePrice<?>> zoneClass;

    private PriceType(Class<? extends Price.PlayerPrice<?>> playerClass, Class<?
            extends Price.ZonePrice<?>> zoneClass){
        this.playerClass = playerClass;
        this.zoneClass = zoneClass;
    }

    public Optional<Class<? extends Price.PlayerPrice<?>>> getPlayerClass(){
        return Optional.of(this.playerClass);
    }

    public Optional<Class<? extends Price.ZonePrice<?>>> getZoneClass(){
        return Optional.of(this.zoneClass);
    }


}
