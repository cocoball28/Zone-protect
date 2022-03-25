package org.zone.region.flag.meta.edit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;

import java.io.IOException;
import java.util.Optional;

/**
 * Flag used to state that a zone is being modified
 *
 * @since 1.0.0
 */
public class EditingFlagType implements FlagType.SerializableType<EditingFlag> {

    public static final String NAME = "Editing";
    public static final String KEY = "edit";

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
    public @NotNull EditingFlag load(@NotNull ConfigurationNode node) throws IOException {
        throw new IOException("Editing is not serializable");
    }

    @Override
    public void save(@NotNull ConfigurationNode node, @Nullable EditingFlag save) {

    }

    @Override
    public @NotNull Optional<EditingFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
