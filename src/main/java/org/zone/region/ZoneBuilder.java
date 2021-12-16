package org.zone.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.plugin.PluginContainer;
import org.zone.region.bounds.ChildRegion;
import org.zone.region.bounds.mode.BoundMode;
import org.zone.region.bounds.mode.BoundModes;
import org.zone.region.flag.Flag;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class ZoneBuilder {

    private PluginContainer container;
    private ChildRegion region;
    private String key;
    private String name;
    private Collection<Flag> flags = new HashSet<>();
    private String parentId;
    private BoundMode boundMode;

    public void setBoundMode(@Nullable BoundMode mode) {
        this.boundMode = mode;
    }

    public @NotNull BoundMode getBoundMode() {
        if (this.boundMode == null) {
            return BoundModes.BLOCK;
        }
        return this.boundMode;
    }

    public PluginContainer getContainer() {
        return this.container;
    }

    public ZoneBuilder setContainer(PluginContainer container) {
        this.container = container;
        return this;
    }

    public ChildRegion getRegion() {
        return this.region;
    }

    public ZoneBuilder setRegion(ChildRegion region) {
        this.region = region;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public ZoneBuilder setKey(String key) {
        this.key = key;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ZoneBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Collection<Flag> getFlags() {
        return this.flags;
    }

    public ZoneBuilder setFlags(Collection<Flag> flags) {
        this.flags = flags;
        return this;
    }

    public ZoneBuilder addFlags(Flag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    public String getParentId() {
        return this.parentId;
    }

    public ZoneBuilder setParent(@SuppressWarnings("TypeMayBeWeakened") Zone parent) {
        this.parentId = parent.getId();
        return this;
    }

    public ZoneBuilder setParentId(String id) {
        this.parentId = id;
        return this;
    }

    public Zone build() {
        return new Zone(this);
    }

}
