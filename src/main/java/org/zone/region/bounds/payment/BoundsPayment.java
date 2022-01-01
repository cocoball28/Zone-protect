package org.zone.region.bounds.payment;

import net.kyori.adventure.bossbar.BossBar;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;

public interface BoundsPayment {

    BossBar onUpdate(Player player, ZoneBuilder builder);

    void onPayment(Player player, Zone zone);
}
