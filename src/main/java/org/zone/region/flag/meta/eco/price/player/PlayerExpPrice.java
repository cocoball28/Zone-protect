package org.zone.region.flag.meta.eco.price.player;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.meta.eco.price.Price;
import org.zone.region.flag.meta.eco.price.PriceBuilder;
import org.zone.region.flag.meta.eco.price.PriceType;

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
    public float getPercentLeft(@NotNull Player player) {
        int exp = player.get(Keys.EXPERIENCE).orElse(0);
        int difference = exp - this.exp;
        return (float)(difference * 100.0 / exp);
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
        return Component.text(this.exp + "EXP");
    }
}
