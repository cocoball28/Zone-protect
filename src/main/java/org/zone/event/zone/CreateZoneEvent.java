package org.zone.event.zone;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Cause;
import org.zone.region.Zone;

public class CreateZoneEvent implements ZoneEvent {

    public static class Pre extends CreateZoneEvent implements Cancellable {

        private boolean isCancelled;

        public Pre(Zone zone, Cause cause) {
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

    public static class Post extends CreateZoneEvent {

        public Post(Zone zone, Cause cause) {
            super(zone, cause);
        }
    }

    private final Zone zone;
    private final Cause cause;

    public CreateZoneEvent(Zone zone, Cause cause) {
        this.zone = zone;
        this.cause = cause;
    }

    @Override
    public Zone getZone() {
        return this.zone;
    }

    @Override
    public Cause cause() {
        return this.cause;
    }
}
