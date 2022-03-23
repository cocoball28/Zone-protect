package org.zone.region.flag.entity.player.display;

import org.zone.Identifiable;
import org.zone.Serializable;
import org.zone.annotations.Typed;

@Typed(typesClass = MessageDisplayTypes.class)
public interface MessageDisplayType<T extends MessageDisplay> extends Identifiable, Serializable<T> {

    /**
     * Creates an object of the display class
     *
     * @return new object of the display class
     */
    T createCopyOfDefault();

}
