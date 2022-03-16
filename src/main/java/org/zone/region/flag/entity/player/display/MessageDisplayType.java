package org.zone.region.flag.entity.player.display;

import org.zone.Identifiable;
import org.zone.Serializable;

public interface MessageDisplayType<T extends MessageDisplay> extends Identifiable, Serializable<T> {

    T createCopyOfDefault();

}
