package org.zone.region.group;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Used for looping over groups
 */
public class ImplementedGroupIterator implements Iterator<Group> {

    private final Group rootGroup;
    private Group targetGroup;

    public ImplementedGroupIterator(Group group) {
        this.rootGroup = group;
        this.reset();
    }

    public void reset() {
        this.targetGroup = this.rootGroup;
    }

    @Override
    public boolean hasNext() {
        return this.targetGroup != null;
    }

    @Override
    public Group next() {
        if (this.targetGroup == null) {
            throw new NoSuchElementException("Out of next groups");
        }
        Group ret = this.targetGroup;
        this.targetGroup = ret.getParent().orElse(null);
        return ret;
    }
}
