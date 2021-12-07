package org.zone.utils.component.parsers;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;

import java.util.Collection;

public interface ComponentPartParser {

    boolean hasTag(@NotNull String tag);

    boolean hasTag(@NotNull Component component);

    @NotNull Component withTag(@NotNull String tag, @NotNull Component component);

    @NotNull String withTag(@NotNull Component component, @NotNull String message);

    @NotNull Collection<CommandCompletion> getSuggestions(@NotNull String peek);
}
