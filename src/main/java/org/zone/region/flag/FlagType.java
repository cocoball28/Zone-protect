package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.zone.Identifiable;

import java.io.IOException;

public interface FlagType<F extends Flag> extends Identifiable {

    @NotNull F load(@NotNull ConfigurationNode node) throws IOException;

    void save(@NotNull ConfigurationNode node, @NotNull F save) throws IOException;
}
