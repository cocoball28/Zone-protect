package org.zone.region.flag.entity.player.display;

import org.zone.ZonePlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MessageDisplayManager {

    private final Collection<MessageDisplayType<?>> displayTypes =
            new HashSet<>();

    public MessageDisplayManager() {
        this.displayTypes.addAll(ZonePlugin
                .getZonesPlugin()
                .getVanillaTypes(MessageDisplayType.class)
                .map(type -> (MessageDisplayType<?>) type)
                .collect(Collectors.toSet()));
    }

    /**
     * Get the registered display types
     *
     * @return The display types available
     */
    public Collection<MessageDisplayType<?>> getDisplayTypes() {
        return Collections.unmodifiableCollection(this.displayTypes);
    }

    /**
     * Register a new display type
     *
     * @param displayType The display type
     */
    public void registerDisplay(MessageDisplayType<?> displayType) {
        this.displayTypes.add(displayType);
    }
}
