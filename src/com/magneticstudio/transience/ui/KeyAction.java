package com.magneticstudio.transience.ui;

/**
 * This interface simply provides functions
 * that deal with the user's keyboard input.
 *
 * @author Max
 */
public interface KeyAction {

    /**
     * Function that gets called when this
     * key is pressed.
     */
    void onPress();

    /**
     * Function that gets called when this
     * key is released.
     */
    void onRelease();
}
