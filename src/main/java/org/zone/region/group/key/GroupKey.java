package org.zone.region.group.key;

import org.zone.Identifiable;
import org.zone.annotations.Typed;

/**
 * A key designed to be registered with a group which can be used to provide more information about this group, such as if this group can bypass a flag
 */
@Typed(typesClass = GroupKeys.class)
public interface GroupKey extends Identifiable {


}
