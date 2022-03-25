package org.zone.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a class has a "Types" class, use this annotation on the type class which then points to the
 * Types class for it.
 *
 * This then allows for {@link org.zone.ZonePlugin#getVanillaTypes(Class)} usage
 * @since 1.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Typed {

    /**
     * The class holding all vanilla types of this assigned class
     *
     * @return The class that holds all vanilla versions of the assigned class
     */
    Class<?> typesClass();
}
