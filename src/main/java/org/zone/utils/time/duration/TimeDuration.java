package org.zone.utils.time.duration;

import org.jetbrains.annotations.NotNull;
import org.zone.utils.time.TimeUnits;

import java.time.Duration;

public class TimeDuration {

    private final @NotNull TimeUnits timeUnit;
    private final int length;

    /**
     * Constructor of this class
     *
     * @param timeUnit The time unit
     * @param length    The duration
     */
    public TimeDuration(@NotNull TimeUnits timeUnit, int length) {
        this.timeUnit = timeUnit;
        this.length = length;
    }

    /**
     * Gets the time unit
     *
     * @return The time unit
     */
    public @NotNull TimeUnits getTimeUnit() {
        return this.timeUnit;
    }

    /**
     * Gets the duration
     *
     * @return The duration
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Gets the {@link Duration} version of this
     *
     * @return The Duration version of this
     */
    public @NotNull Duration asDuration() {
        return Duration.of(this.length, this.timeUnit.getUnit());
    }
}
