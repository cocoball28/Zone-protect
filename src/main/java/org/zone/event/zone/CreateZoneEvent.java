package org.zone.event.zone;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Cause;
import org.zone.region.Zone;

/**
 * Fired when a zone is created
 *
 * @since 1.0.1
 */
@SuppressWarnings("AbstractClassWithoutAbstractMethods")
public abstract class CreateZoneEvent implements ZoneEvent {

    private final @NotNull Zone zone;
    private final @NotNull Cause cause;

    /**
     * Fired just before the zone is created
     *
     * @since 1.0.1
     */
    public static class Pre extends CreateZoneEvent implements Cancellable {

        private boolean isCancelled;

        public Pre(@NotNull Zone zone, @NotNull Cause cause) {
            super(zone, cause);
        }

        @Override
        public boolean isCancelled() {
            return this.isCancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.isCancelled = cancel;
        }
    }

    /**
     * Fired after the zone is created
     *
     * @since 1.0.1
     */
    public static class Post extends CreateZoneEvent {

        public Post(@NotNull Zone zone, @NotNull Cause cause) {
            super(zone, cause);
        }
    }

    public CreateZoneEvent(@NotNull Zone zone, @NotNull Cause cause) {
        this.zone = zone;
        this.cause = cause;
    }

    @Override
    public @NotNull Zone getZone() {
        return this.zone;
    }

    @Override
    public @NotNull Cause cause() {
        return this.cause;
    }
}
