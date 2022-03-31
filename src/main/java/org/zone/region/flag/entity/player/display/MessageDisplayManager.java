package org.zone.region.flag.entity.player.display;

import org.jetbrains.annotations.NotNull;
import org.zone.IdentifiableManager;
import org.zone.ZonePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MessageDisplayManager implements IdentifiableManager.Typed<MessageDisplayType<?>> {

    private final Collection<MessageDisplayType<?>> displayTypes =
            new HashSet<>(ZonePlugin
                    .getZonesPlugin()
                    .getVanillaTypes(MessageDisplayType.class)
                    .map(type -> (MessageDisplayType<?>) type)
                    .collect(Collectors.toSet()));

    /**
     * Gets all message display types
     *
     * @return A collection of message display types
     * @since 1.0.1
     */
    @Override
    public Collection<MessageDisplayType<?>> getRegistered() {
        return this.displayTypes;
    }

    /**
     * Register a new display type
     *
     * @param type The display type
     * @since 1.0.1
     */
    @Override
    public void register(@NotNull MessageDisplayType<?> type) {
        this.displayTypes.add(type);
    }
}
