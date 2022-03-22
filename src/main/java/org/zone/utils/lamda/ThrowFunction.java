package org.zone.utils.lamda;

import java.util.function.Function;

/**
 * A throwable edition of {@link Function}, this can be treated as a regular function with it
 * throwing {@link IllegalStateException} if a exception is thrown
 *
 * @param <T> The original type
 * @param <E> The new type
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
