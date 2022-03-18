package org.zone.utils.time.tick;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public enum TickTime implements TemporalUnit {
    TICK(Duration.of(50000, ChronoUnit.MICROS));

    private final Duration duration;

    TickTime(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public boolean isDurationEstimated() {
        return true;
    }

    @Override
    public boolean isDateBased() {
        return false;
    }

    @Override
    public boolean isTimeBased() {
        return true;
    }

    @Override
    public <R extends Temporal> R addTo(R temporal, long amount) {
        return (R) temporal.plus(amount, this);
    }

    @Override
    public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return temporal1Inclusive.until(temporal2Exclusive, this);

    }
}
