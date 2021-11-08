package org.zone.region;

import org.spongepowered.plugin.PluginContainer;
import org.zone.region.flag.Flag;
import org.zone.region.group.Group;
import org.zone.region.regions.Region;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class ZoneBuilder {

    private PluginContainer container;
    private Region region;
    private String key;
    private String name;
    private Collection<Flag> flags = new HashSet<>();
    private Collection<Group> groups = new HashSet<>();
    private Zone parent;

    public PluginContainer getContainer() {
        return this.container;
    }

    public ZoneBuilder setContainer(PluginContainer container) {
        this.container = container;
        return this;
    }

    public Region getRegion() {
        return this.region;
    }

    public ZoneBuilder setRegion(Region region) {
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

    public Collection<Group> getGroups() {
        return this.groups;
    }

    public ZoneBuilder setGroups(Collection<Group> groups) {
        this.groups = groups;
        return this;
    }

    public ZoneBuilder addGroups(Group... groups) {
        this.groups.addAll(Arrays.asList(groups));
        return this;
    }

    public Zone getParent() {
        return this.parent;
    }

    public ZoneBuilder setParent(Zone parent) {
        this.parent = parent;
        return this;
    }

    public Zone build() {
        return new Zone(this);
    }

}
