package com.magneticstudio.transience.ui;

/**
 * This interface provides a set of functions
 * that may be implemented by UIMenu objects
 * to process mouse events coming from MenuMouse.
 *
 * @author Max
 * @see MenuMouse Details on how key processing is handled.
 */
public interface MenuMouseInterceptor {

    /**
     * Dispatches a mouse event.
     * @param type The type of event.
     * @param eventButton The button of the event.
     */
    void onMouseEvent(MouseEventType type, Integer eventButton);
}
