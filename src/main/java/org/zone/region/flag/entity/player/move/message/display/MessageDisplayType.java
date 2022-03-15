package org.zone.region.flag.entity.player.move.message.display;

import org.zone.Identifiable;
import org.zone.Serializable;

public interface MessageDisplayType<T extends MessageDisplay> extends Identifiable, Serializable<T> {

    T createCopyOfDefault();

}
