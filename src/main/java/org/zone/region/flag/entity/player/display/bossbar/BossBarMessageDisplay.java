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

    public BossBarMessageDisplay(@NotNull BossBarMessageDisplayBuilder builder) {
        this.progress = builder.getProgress();
        this.color = builder.getColor();
        this.overlay = builder.getOverlay();
    }

    /**
     * Gets the progress of the {@link BossBar}
     *
     * @return The progress
     */
    public float getProgress() {
        return this.progress;
    }

    /**
     * Gets the colour of the {@link BossBar}
     *
     * @return The colour
     */
    public @NotNull BossBar.Color getColor() {
        return this.color;
    }

    /**
     * Gets the overlay of the {@link BossBar}
     *
     * @return The overlay
     */
    public @NotNull BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    /**
     * Sets the progress of the {@link BossBar}
     *
     * @param progress The progress to be set
     */
    public void setBossBarProgress(float progress) {
        this.progress = progress;
    }

    /**
     * Sets the colour of the {@link BossBar}
     *
     * @param color The colour to be set
     * @see BossBar.Color
     */
    public void setBossBarColor(@NotNull BossBar.Color color) {
        this.color = color;
    }

    /**
     * Sets the overlay for the {@link BossBar}
     *
     * @param overlay The overlay to be set
     * @see BossBar.Overlay
     */
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
