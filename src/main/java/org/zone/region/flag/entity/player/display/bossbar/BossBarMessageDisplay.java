package org.zone.region.flag.entity.player.display.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.MessageDisplayType;
import org.zone.region.flag.entity.player.display.MessageDisplayTypes;

public class BossBarMessageDisplay implements MessageDisplay {

    private float progress;
    private @NotNull BossBar.Color color;
    private @NotNull BossBar.Overlay overlay;

    public BossBarMessageDisplay(float progress, @NotNull BossBar.Color color,
            @NotNull BossBar.Overlay overlay) {
        this.progress = progress;
        this.color = color;
        this.overlay = overlay;
    }

    public BossBarMessageDisplay() {}

    public float getProgress() {
        return this.progress;
    }

    public @NotNull BossBar.Color getColor() {
        return this.color;
    }

    public @NotNull BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    public void setBossBarProgress(float progress) {
        this.progress = progress;
    }

    public void setBossBarColor(@NotNull BossBar.Color color) {
        this.color = color;
    }

    public void setBossBarOverlay(@NotNull BossBar.Overlay overlay) {
        this.overlay = overlay;
    }

    @Override
    public void sendMessage(@NotNull Component message, @NotNull Player receiver) {
        BossBar bossBarMessage = BossBar
                .bossBar(message,
                        this.progress,
                        this.color,
                        this.overlay);
        receiver.showBossBar(bossBarMessage);
    }

    @Override
    public @NotNull MessageDisplayType<? extends MessageDisplay> getType() {
        return MessageDisplayTypes.BOSS_BAR;
    }


}
