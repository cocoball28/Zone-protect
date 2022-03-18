package org.zone.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is sealed annotation designed to repersent classes that will be sealed when it gets
 * released into Java
 */
@Retention(RetentionPolicy.CLASS)
public @interface Sealed {

    /**
     * The classes that should be the only classes implemented the class that this is assigned to
     *
     * @return A list of classes that should be sealed
     */
    Class<?>[] classes();
}
