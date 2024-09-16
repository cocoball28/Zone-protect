package org.zone.event.zone;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Cause;
import org.zone.region.Zone;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;

/**
 * Fired when a flag is either remove, set or replaced
 *
 * @since 1.0.1
 */
public abstract class FlagChangeEvent implements ZoneEvent {

    private final @NotNull Cause cause;
    private final @NotNull Zone zone;

    /**
     * Fired when a flag is replaced
     *
     * @since 1.0.1
     */
    public static class UpdateFlag extends FlagChangeEvent implements Cancellable {

        private final @NotNull Flag previous;
        private final @NotNull Flag updated;
        private boolean isCancelled;

        public UpdateFlag(
                Zone zone, @NotNull Flag previous, @NotNull Flag updated, @NotNull Cause cause) {
            super(zone, cause);
            this.previous = previous;
            this.updated = updated;
        }

        @Override
        public boolean isCancelled() {
            return this.isCancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.isCancelled = cancel;
        }

        @Override
        public @NotNull FlagType<?> getFlagType() {
            return this.updated.getType();
        }

        public @NotNull Flag getPrevious() {
            return this.previous;
        }

        public @NotNull Flag getUpdated() {
            return this.updated;
        }
    }

    /**
     * Fired when a flag is removed
     *
     * @since 1.0.1
     */
    public static class RemoveFlag extends FlagChangeEvent implements Cancellable {

        private final @NotNull FlagType<?> type;
        private boolean cancelled;

        public RemoveFlag(@NotNull Zone zone, @NotNull FlagType<?> type, @NotNull Cause cause) {
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
        public @NotNull FlagType<?> getFlagType() {
            return this.type;
        }
    }

    /**
     * Fired when a flag is added
     *
     * @since 1.0.1
     */
    public static class AddFlag extends FlagChangeEvent implements Cancellable {

        private final @NotNull Flag flag;
        private boolean isCancelled;

        public AddFlag(@NotNull Zone zone, @NotNull Flag flag, @NotNull Cause cause) {
            super(zone, cause);
            this.flag = flag;
        }

        public @NotNull Flag getFlag() {
            return this.flag;
        }

        @Override
        public @NotNull FlagType<?> getFlagType() {
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

    public FlagChangeEvent(@NotNull Zone zone, @NotNull Cause cause) {
        this.cause = cause;
        this.zone = zone;
    }

    public abstract @NotNull FlagType<?> getFlagType();


    @Override
    public @NotNull Zone getZone() {
        return this.zone;
    }

    @Override
    public @NotNull Cause cause() {
        return this.cause;
    }
}
