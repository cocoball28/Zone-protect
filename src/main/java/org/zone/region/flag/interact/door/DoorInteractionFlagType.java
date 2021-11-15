package org.zone.region.flag.interact.door;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

import java.io.IOException;
import java.util.Optional;

public class DoorInteractionFlagType implements FlagType<DoorInteractionFlag> {

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
    public @NotNull DoorInteractionFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean isEmpty = node.node("Enabled").isNull();
        if (isEmpty) {
            throw new IOException("Cannot load flag");
        }
        boolean enabled = node.node("Enabled").getBoolean();
        String groupId = node.node("Group").getString();
        if (groupId==null) {
            throw new IOException("No group could be found");
        }
        return new DoorInteractionFlag(groupId, enabled);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable DoorInteractionFlag save) throws IOException {
        if (save==null || save.getValue().isEmpty()) {
            node.set(null);
            return;
        }
        Boolean value = save.getValue().get();
        String groupId = save.getGroupId();

        node.node("Enabled").set(value);
        node.node("Group").set(groupId);
    }

    @Override
    public boolean canApply(Zone zone) {
        return true;
    }

    @Override
    public Optional<DoorInteractionFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }

    @Override
    public int compareTo(@NotNull FlagType<?> o) {
        if (o.equals(FlagTypes.MEMBERS)) {
            return -1;
        }
        return FlagType.super.compareTo(o);
    }
}
