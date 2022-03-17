package org.zone.region.shop.transaction.price.player;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.utils.Messages;
import org.zone.region.shop.transaction.price.Price;
import org.zone.region.shop.transaction.price.PriceType;

public class PlayerExpPrice implements Price.PlayerPrice<Integer> {

    private final int exp;

    public PlayerExpPrice(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return this.exp;
    }

    @Override
    public boolean hasEnough(@NotNull Player player) {
        return player.get(Keys.EXPERIENCE).orElse(0) <= this.exp;
    }

    @Override
    public boolean withdraw(Player player) {
        int exp = player.get(Keys.EXPERIENCE).orElse(0);
        if (exp < this.exp) {
            return false;
        }
        int difference = exp - this.exp;
        player.offer(Keys.EXPERIENCE, difference);
        return true;
    }

    @Override
    public float getPercentLeft(@NotNull Player player) {
        int exp = player.get(Keys.EXPERIENCE).orElse(0);
        if (this.exp == 0) {
            return 0;
        }
        if (exp == 0) {
            return 0;
        }
        int difference = exp - this.exp;
        return (float) (difference * 100.0 / exp);
    }

    @Override
    public Integer getAmount() {
        return this.exp;
    }

    @Override
    public PriceType getType() {
        return PriceType.EXP;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Messages.getExp(this.exp);
    }
}
