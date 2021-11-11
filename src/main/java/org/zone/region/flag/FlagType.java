package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.zone.Identifiable;
import org.zone.region.Zone;

import java.io.IOException;
import java.util.Optional;

public interface FlagType<F extends Flag<?, ?>> extends Identifiable {

    @NotNull F load(@NotNull ConfigurationNode node) throws IOException;

    void save(@NotNull ConfigurationNode node, @Nullable F save) throws IOException;

    boolean canApply(Zone zone);

    Optional<F> createDefaultFlag();
}
