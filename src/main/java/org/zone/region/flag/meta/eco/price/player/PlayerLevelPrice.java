package org.zone.region.flag.meta.eco.price.player;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.meta.eco.price.Price;

public class PlayerLevelPrice implements Price.PlayerPrice {

    private final int exp;

    public PlayerLevelPrice(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return this.exp;
    }

    @Override
    public boolean hasEnough(Player player) {
        return player.get(Keys.EXPERIENCE_LEVEL).orElse(0) <= this.exp;
    }

    @Override
    public Component getDisplayName() {
        return Component.text("Level: " + this.exp);
    }
}
