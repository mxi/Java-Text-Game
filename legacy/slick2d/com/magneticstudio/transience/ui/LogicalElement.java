package com.magneticstudio.transience.ui;

/**
 * This interface defines a function that must be
 * implemented by any object that wishes to be
 * called when the function "update()" in a Game
 * object gets called.
 *
 * @author Max
 */
public interface LogicalElement {

    /**
     * Updates this object using elapsed time
     * to gage how much change is needed.
     * @param milliseconds The elapsed time since last update.
     */
    void update(int milliseconds);
}
