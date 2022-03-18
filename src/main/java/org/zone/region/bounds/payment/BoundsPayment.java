package org.zone.region.bounds.payment;

import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;

public interface BoundsPayment {

    BossBar onUpdate(@NotNull Player player, @NotNull ZoneBuilder builder);

    void onPayment(@NotNull Player player, @NotNull Zone zone);
}
