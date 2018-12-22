package com.magneticstudio.transience.ui;

/**
 * This interface provides a set of functions
 * that may be implemented by UIMenu objects
 * to process keyboard events coming from MenuKeyboard.
 *
 * @author Max
 * @see MenuKeyboard Details on how key processing is handled.
 */
public interface MenuKeyboardInterceptor {

    /**
     * Dispatches a key event.
     * @param type The type of event.
     * @param eventCharacter The key/character of the event.
     */
    void onKeyEvent(KeyboardEventType type, Character eventCharacter);
}
