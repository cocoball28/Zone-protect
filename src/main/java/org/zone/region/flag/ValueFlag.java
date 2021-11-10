package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface ValueFlag<F, G> extends Flag {

    interface Single<F> extends ValueFlag<F, Optional<F>> {

    }

    interface Multiple<V> extends ValueFlag<Collection<V>, Collection<V>> {

        @Override
        default void removeValue() {
            this.setValue(Collections.emptyList());
        }
    }

    interface Map<K, V> extends ValueFlag<java.util.Map<K, V>, java.util.Map<K, V>> {

    }

    @NotNull G getValue();

    void setValue(@Nullable F flag);

    default void removeValue() {
        this.setValue(null);
    }

}
