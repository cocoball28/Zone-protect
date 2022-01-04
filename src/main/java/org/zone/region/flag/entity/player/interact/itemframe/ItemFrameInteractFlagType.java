package org.zone.region.flag.entity.player.interact.itemframe;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

public class ItemFrameInteractFlagType implements FlagType.TaggedFlagType<ItemFrameInteractFlag> {

    @Override
    public @NotNull String getName() {
        return "Item_frame Interact";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "interact_item_frame";
    }

    @Override
    public ItemFrameInteractFlag createCopyOfDefault() {
        return new ItemFrameInteractFlag();
    }
}
