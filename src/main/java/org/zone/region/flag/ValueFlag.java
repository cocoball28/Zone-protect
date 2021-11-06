package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ValueFlag<F> extends Flag {

    @NotNull Optional<F> getValue();

    void setValue(@Nullable F flag);

    default void removeValue(){
        this.setValue(null);
    }

}
