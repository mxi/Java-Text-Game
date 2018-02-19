package com.magneticstudio.transience.ui;

import org.lwjgl.input.Keyboard;

/**
 * This class handles managing keyboard inputs when
 * a menu window us up. This is mainly used for
 * keyboard input into text fields or areas.
 *
 * @author Max
 */
public class MenuKeyboard {

    public static int HOLD_TIME = 500; // Time in milliseconds until a key is considered held.
    public static int CONSECUTIVE_HOLD_PRESS = 50; // Time in milliseconds until a hold event is dispatched.
    public static boolean DISCARD_FUNCTION_KEYS = true; // Ignores function keys such as shift, ctrl, and alt.

    private static Character key = (char) -1;
    private static KeyProfile profile;

    /**
     * Registers the next key press
     * and dispatches it to a menu
     * if necessary.
     */
    public static void poll() {
        if(!Keyboard.getEventKeyState()) { // Not a press event (ie. release)
            key = (char) -1;
            profile = null;
            return;
        }

        char eventKey = Keyboard.getEventCharacter();
        if(DISCARD_FUNCTION_KEYS && eventKey == 0)
            return;

        if(eventKey != key) {
            key = eventKey;
            profile = new KeyProfile();
            // TODO: Dispatch press event.
            return;
        }

        long currentTime = System.currentTimeMillis();
        long keyTime = profile.lastEventTime;

        if(profile.isTapped && currentTime - keyTime >= HOLD_TIME) {
            profile.lastEventTime = currentTime;
            profile.isTapped = false;
            // TODO: Dispatch hold event.
        }
        else if(!profile.isTapped && currentTime - keyTime >= CONSECUTIVE_HOLD_PRESS) {
            profile.lastEventTime = currentTime;
            // TODO: Dispatch hold event.
        }
    }

    /**
     * This struct is used to aid in storing
     * information about a pressed key when
     * using a text-field type of input.
     */
    private static final class KeyProfile {

        private long lastEventTime = System.currentTimeMillis();
        private boolean isTapped = true;

    }
}
