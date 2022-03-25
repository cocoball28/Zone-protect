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
     * @since 1.0.1
     */
    public float getProgress() {
        return this.progress;
    }

    /**
     * Gets the colour of the {@link BossBar}
     *
     * @return The colour
     * @since 1.0.1
     */
    public @NotNull BossBar.Color getColor() {
        return this.color;
    }

    /**
     * Gets the overlay of the {@link BossBar}
     *
     * @return The overlay
     * @since 1.0.1
     */
    public @NotNull BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    /**
     * Sets the progress of the {@link BossBar}
     *
     * @param progress The progress to be set
     * @since 1.0.1
     */
    public void setBossBarProgress(float progress) {
        this.progress = progress;
    }

    /**
     * Sets the colour of the {@link BossBar}
     *
     * @param color The colour to be set
     * @since 1.0.1
     */
    public void setBossBarColor(@NotNull BossBar.Color color) {
        this.color = color;
    }

    /**
     * Sets the overlay for the {@link BossBar}
     *
     * @param overlay The overlay to be set
     * @since 1.0.1
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
