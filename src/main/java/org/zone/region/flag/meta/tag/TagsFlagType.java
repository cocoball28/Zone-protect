package org.zone.region.flag.meta.tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TagsFlagType implements FlagType<TagsFlag> {

    public static final String NAME = "Tags";
    public static final String KEY = "tags";

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
    public @NotNull TagsFlag load(@NotNull ConfigurationNode node) throws IOException {
        List<String> tags = node.node("tags").getList(String.class);
        if (tags == null) {
            return new TagsFlag();
        }
        Set<Flag.TaggedFlag> tagFlags = tags
                .parallelStream()
                .map(id -> ZonePlugin
                        .getZonesPlugin()
                        .getFlagManager()
                        .getRegistered()
                        .parallelStream()
                        .filter(type -> type instanceof Flag.TaggedFlag)
                        .filter(type -> type.getId().equals(id))
                        .findAny()
                        .map(type -> ((FlagType.TaggedFlagType<?>) type).createCopyOfDefault()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        return new TagsFlag(tagFlags);
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable TagsFlag save) throws IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        Collection<String> collection = save
                .getTags()
                .parallelStream()
                .map(flag -> flag.getType().getId())
                .collect(Collectors.toSet());
        node.node("tags").set(collection);
    }

    @Override
    public @NotNull Optional<TagsFlag> createCopyOfDefaultFlag() {
        return Optional.of(new TagsFlag());
    }
}
