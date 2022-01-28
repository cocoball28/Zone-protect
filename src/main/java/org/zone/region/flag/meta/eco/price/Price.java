package org.zone.region.flag.meta.eco.price;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.Zone;

public interface Price<O> {

    interface PlayerPrice extends Price<Player> {

    }

    interface ZonePrice extends Price<Zone> {

    }

    boolean hasEnough(O player);

    float getPercentLeft(O player);

    Component getDisplayName();
}
