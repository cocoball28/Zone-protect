package org.zone.region.flag.interact.door;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

/**
 * A flag to check if a player can open/close a door found within a region.
 * <p>
 * If the player is within a group that has the specified GroupKey then they can open/close blocks even with the flag enabled
 */
public class DoorInteractionFlagType implements FlagType.TaggedFlagType<DoorInteractionFlag> {

    public static final String NAME = "Door Interaction";
    public static final String KEY = "interact_door";

    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return KEY;
    }

    @Override
    public DoorInteractionFlag createCopyOfDefault() {
        return new DoorInteractionFlag();
    }
}
