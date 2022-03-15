package org.zone.region.flag.entity.player.move.message.display.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplay;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayType;
import org.zone.region.flag.entity.player.move.message.display.MessageDisplayTypes;

public class BossBarMessageDisplay implements MessageDisplay {

    @Override
    public void sendMessage(@NotNull Component message, @NotNull Player receiver) {
        BossBar bossBarMessage = BossBar
                .bossBar(message,
                        100,
                        BossBar.Color.BLUE,
                        BossBar.Overlay.PROGRESS);
        receiver.showBossBar(bossBarMessage);
    }

    @Override
    public @NotNull MessageDisplayType<?> getType() {
        return MessageDisplayTypes.BOSS_BAR_MESSAGE_DISPLAY;
    }

}
