package org.zone.utils.lamda;

import java.util.function.Function;

/**
 * A throwable edition of {@link Function}, this can be treated as a regular function with it
 * throwing {@link IllegalStateException} if an exception is thrown
 *
 * @param <T> The original type
 * @param <E> The new type
 * @since 1.0.1
 */
public interface ThrowFunction<T, E> extends Function<T, E> {

    @Override
    default E apply(T t) {
        try {
            return this.applyThrow(t);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    E applyThrow(T t) throws Exception;
}
