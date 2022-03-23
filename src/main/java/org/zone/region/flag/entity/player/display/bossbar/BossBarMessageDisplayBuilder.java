package org.zone.region.flag.entity.player.display.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

public class BossBarMessageDisplayBuilder {

    private float progress;
    private BossBar.Color color;
    private BossBar.Overlay overlay;

    /**
     * Gets the {@link BossBar#progress()} for the {@link BossBar}
     *
     * @return The progress for the {@link BossBar}
     */
    public float getProgress() {
        return this.progress;
    }

    /**
     * Gets the {@link BossBar#color()} for the {@link BossBar}
     *
     * @return The color for the {@link BossBar}
     */
    public BossBar.Color getColor() {
        return this.color;
    }

    /**
     * Gets the {@link BossBar#overlay()} for the {@link BossBar}
     *
     * @return The overlay for the {@link BossBar}
     */
    public BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    /**
     * Sets the {@link BossBar#progress()} for the {@link BossBar}
     *
     * @param progress The progress to be set
     * @return         This builder class
     */
    public @NotNull BossBarMessageDisplayBuilder setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    /**
     * Sets the {@link BossBar#color()} for the {@link BossBar}
     *
     * @param color The color to be set
     * @return      This builder class
     */
    public @NotNull BossBarMessageDisplayBuilder setColor(BossBar.Color color) {
        this.color = color;
        return this;
    }

    /**
     * Sets the {@link BossBar#overlay()} for the {@link BossBar}
     *
     * @param overlay The overlay to be set
     * @return        This builder class
     */
    public @NotNull BossBarMessageDisplayBuilder setOverlay(BossBar.Overlay overlay) {
        this.overlay = overlay;
        return this;
    }

    /**
     * Builds the {@link BossBarMessageDisplay} with the values desired
     *
     * @return new {@link BossBarMessageDisplay} with the builder being this
     */
    public @NotNull BossBarMessageDisplay build() {
        return new BossBarMessageDisplay(this);
    }
}
