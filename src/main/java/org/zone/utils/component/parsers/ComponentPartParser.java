package org.zone.utils.component.parsers;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface ComponentPartParser {

    boolean hasTag(@NotNull String tag);

    boolean hasTag(@NotNull Component component);

    @NotNull Component withTag(@NotNull String tag, @NotNull Component component);

    @NotNull String withTag(@NotNull Component component, @NotNull String message);

    @NotNull Collection<String> getSuggestions(@NotNull String peek);
}
