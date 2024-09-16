package org.zone;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

/**
 * This class is only for a manager class and can be implemented
 * A manager is a special type of class that is used to have all kinds od data from a types class
 * or any other class
 *
 * @param <I> The class that extends {@link Identifiable}
 * @since 1.0.1
 */
public interface IdentifiableManager<I extends Identifiable> {

    /**
     * This class may be used for a class which has a types class
     *
     * @param <I> The class which extends identifiable and has a types class
     * @since 1.0.1
     */
    interface Typed<I extends Identifiable> extends IdentifiableManager<I> {

        /**
         * Gets a type object from the given id
         *
         * @param id The id of the type
         * @return Any type class that is equal to the provided id
         * @since 1.0.1
         */
        default @NotNull Optional<I> getType(String id) {
            return this
                    .getRegistered()
                    .stream()
                    .filter(type -> type.getId().equalsIgnoreCase(id))
                    .findAny();
        }

    }

    /**
     * Gets all {@link I}
     *
     * @return A collection of all {@link I}
     * @since 1.0.1
     */
    Collection<I> getRegistered();

    /**
     * Registers a new {@link I}
     *
     * @param type The new {@link I}
     * @since 1.0.1
     */
    void register(@NotNull I type);
}
