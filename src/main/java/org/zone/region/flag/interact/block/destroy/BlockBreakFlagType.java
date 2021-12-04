package org.zone.region.flag.interact.block.destroy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

public class BlockBreakFlagType implements FlagType<BlockBreakFlag> {
    @Override
    public @NotNull String getName() {
        return "Block Break";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "block_break";
    }

    @Override
    public @NotNull BlockBreakFlag load(@NotNull ConfigurationNode node) throws IOException {
        boolean isEmpty = node.node("Enabled").isNull();
        if (isEmpty) {
            throw new IOException("Cannot load flag");
        }
        boolean enabled = node.node("Enabled").getBoolean();
        String groupId = node.node("Group").getString();
        if (groupId==null) {
            throw new IOException("No group could be found");
        }
        return new BlockBreakFlag(groupId, enabled);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable BlockBreakFlag save) throws IOException {
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
    public Optional<BlockBreakFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
