package org.zone.event.zone;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Cause;
import org.zone.region.Zone;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;

public abstract class FlagChangeEvent implements ZoneEvent {

    public static class RemoveFlag extends FlagChangeEvent implements Cancellable {

        private final FlagType<?> type;
        private boolean cancelled;

        public RemoveFlag(Zone zone, FlagType<?> type, Cause cause) {
            super(zone, cause);
            this.type = type;
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }

        @Override
        public FlagType<?> getFlagType() {
            return this.type;
        }
    }

    public static class AddFlag extends FlagChangeEvent implements Cancellable {

        private final Flag flag;
        private boolean isCancelled;

        public AddFlag(Zone zone, Flag flag, Cause cause) {
            super(zone, cause);
            this.flag = flag;
        }

        public Flag getFlag() {
            return this.flag;
        }

        @Override
        public FlagType<?> getFlagType() {
            return this.flag.getType();
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

    private final Cause cause;
    private final Zone zone;

    public FlagChangeEvent(Zone zone, Cause cause) {
        this.cause = cause;
        this.zone = zone;
    }

    public abstract FlagType<?> getFlagType();


    @Override
    public Zone getZone() {
        return this.zone;
    }

    @Override
    public Cause cause() {
        return this.cause;
    }
}
