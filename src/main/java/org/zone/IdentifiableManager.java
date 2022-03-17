package org.zone;

import java.util.Collection;
import java.util.Optional;

public interface IdentifiableManager<I extends Identifiable> {

    interface Typed<I extends Identifiable> extends IdentifiableManager<I> {

        default Optional<I> getType(String id) {
            return this
                    .getRegistered()
                    .stream()
                    .filter(type -> type.getId().equalsIgnoreCase(id))
                    .findAny();
        }

    }

    Collection<I> getRegistered();

    void register(I type);
}
