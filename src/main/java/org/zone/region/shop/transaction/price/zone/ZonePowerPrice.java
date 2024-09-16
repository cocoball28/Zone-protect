package org.zone.region.shop.transaction.price.zone;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.zone.region.Zone;
import org.zone.region.shop.transaction.price.Price;
import org.zone.region.shop.transaction.price.PriceType;
import org.zone.utils.Messages;

/**
 * A price for a zones power level
 *
 * @since 1.0.1
 */
public class ZonePowerPrice implements Price.ZonePrice<Long> {

    private final long price;

    public ZonePowerPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean hasEnough(@NotNull Zone player) {
        long total = player.getMembers().getPowerLevel();
        long difference = total - this.price;
        return difference <= 0;
    }

    @Override
    public boolean withdraw(@NotNull Zone player) {
        return true;
    }

    @Override
    public float getPercentLeft(@NotNull Zone zone) {
        long total = zone.getMembers().getPowerLevel();
        if (total == 0) {
            return 0;
        }
        long difference = total - this.price;
        return (float) (difference * 100.0 / total);
    }

    @Override
    public @NotNull Long getAmount() {
        return this.price;
    }

    @Override
    public @NotNull PriceType getType() {
        return PriceType.POWER;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Messages.getPowerLevel(this.price);
    }
}
