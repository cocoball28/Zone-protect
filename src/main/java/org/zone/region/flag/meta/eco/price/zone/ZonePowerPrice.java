package org.zone.region.flag.meta.eco.price.zone;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.zone.region.Zone;
import org.zone.region.flag.meta.eco.price.Price;
import org.zone.region.flag.meta.eco.price.PriceBuilder;
import org.zone.region.flag.meta.eco.price.PriceType;

public class ZonePowerPrice implements Price.ZonePrice<Long> {

    private final long price;

    public ZonePowerPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean hasEnough(@NotNull Zone player) {
        return false;
    }

    @Override
    public float getPercentLeft(@NotNull Zone zone) {
        long total = zone.getMembers().getPowerLevel();
        long difference = total - this.price;
        return (float) (difference * 100.0 / total);
    }

    @Override
    public Long getAmount() {
        return this.price;
    }

    @Override
    public PriceType getType() {
        return PriceType.POWER;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text(this.price + " levels");
    }
}
