package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface Flag<F, G> {

    interface Single<F> extends Flag<F, Optional<F>> {

        @Override
        default void removeValue() {
            this.setValue(null);
        }
    }

    interface Multiple<V> extends Flag<Collection<V>, Collection<V>> {

        @Override
        default void removeValue() {
            this.setValue(Collections.emptyList());
        }
    }

    interface Map<K, V> extends Flag<java.util.Map<K, V>, java.util.Map<K, V>> {

        @Override
        default void removeValue() {
            this.setValue(Collections.emptyMap());
        }
    }

    @NotNull G getValue();

    void setValue(@Nullable F flag);

    void removeValue();

    @NotNull FlagType<?> getType();

    default <T extends Flag<F, G>> void save(ConfigurationNode node) throws IOException {
        ((FlagType<T>) this.getType()).save(node, (T) this);
    }

}
