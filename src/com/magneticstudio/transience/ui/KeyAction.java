package com.magneticstudio.transience.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This interface simply signs a function
 * as being a key action processor.
 *
 * @author Max
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KeyAction {

    /**
     * The key id of which the function is
     * processing input of.
     * @return The id of the key this function is processing.
     * @see org.newdawn.slick.Input Key codes.
     */
    int key();

    /**
     * The time in milliseconds until the key
     * would be considered "held"
     * @return Time until key is held.
     */
    int untilHeld() default 750;

    /**
     * The time in milliseconds to wait before
     * invoking another call to the function
     * as a "hold" operation/KeyActionType.
     * @return The time before each consecutive hold operation.
     */
    int hold() default 5;
}
