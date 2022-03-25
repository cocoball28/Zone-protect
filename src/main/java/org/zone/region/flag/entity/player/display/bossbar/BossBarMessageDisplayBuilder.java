package org.zone.region.flag.entity.player.display.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

public class BossBarMessageDisplayBuilder {

    private float progress;
    private BossBar.Color color;
    private BossBar.Overlay overlay;

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
    public BossBar.Color getColor() {
        return this.color;
    }

    /**
     * Gets the overlay of the {@link BossBar}
     *
     * @return The overlay
     * @since 1.0.1
     */
    public BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    /**
     * Sets the progress for the {@link BossBar}
     *
     * @param progress The progress to be set
     * @return {@link BossBarMessageDisplayBuilder}
     * @since 1.0.1
     */
    public @NotNull BossBarMessageDisplayBuilder setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    /**
     * Sets the color of the {@link BossBar}
     *
     * @param color The color to be set
     * @return {@link BossBarMessageDisplayBuilder}
     * @since 1.0.1
     */
    public @NotNull BossBarMessageDisplayBuilder setColor(BossBar.Color color) {
        this.color = color;
        return this;
    }

    /**
     * Sets the overlay of the {@link BossBar}
     *
     * @param overlay The overlay to be set
     * @return        {@link BossBarMessageDisplayBuilder}
     * @since 1.0.1
     */
    public @NotNull BossBarMessageDisplayBuilder setOverlay(BossBar.Overlay overlay) {
        this.overlay = overlay;
        return this;
    }

    /**
     * Builds the {@link BossBarMessageDisplay} with the values desired
     *
     * @return new {@link BossBarMessageDisplay} with the builder being this
     * @since 1.0.1
     */
    public @NotNull BossBarMessageDisplay build() {
        return new BossBarMessageDisplay(this);
    }
}
