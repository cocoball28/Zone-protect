package org.zone.region.flag.meta.eco.price.zone;

import net.kyori.adventure.text.Component;
import org.zone.region.Zone;
import org.zone.region.flag.meta.eco.price.Price;

public class ZonePowerPrice implements Price.ZonePrice {

    private final long price;

    public ZonePowerPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean hasEnough(Zone player) {
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.text(this.price + " levels");
    }
}
