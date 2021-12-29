package org.zone.region.flag.meta.tag;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

public class TagsFlag implements Flag {

    private final Collection<TaggedFlag> flags = new HashSet<>();

    public TagsFlag() {
        this(Collections.emptySet());
    }

    public TagsFlag(Collection<? extends TaggedFlag> collection) {
        this.flags.addAll(collection);
    }

    public boolean addTag(TaggedFlag flag) {
        return this.flags.add(flag);
    }

    public void removeTag(FlagType.TaggedFlagType<?> flag) {
        this.flags
                .parallelStream()
                .filter(tf -> tf.getType().equals(flag))
                .findAny()
                .ifPresent(this.flags::remove);
    }

    public <T extends TaggedFlag> Optional<T> getTag(FlagType<T> clazz) {
        return this.flags
                .parallelStream()
                .filter(f -> f.getType().equals(clazz))
                .findAny()
                .map(f -> (T) f);
    }

    public Collection<TaggedFlag> getTags() {
        return Collections.unmodifiableCollection(this.flags);
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return null;
    }
}
