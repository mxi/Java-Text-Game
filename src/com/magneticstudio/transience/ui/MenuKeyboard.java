package com.magneticstudio.transience.ui;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

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

    private static final List<MenuKeyboardInterceptor> interceptors = new ArrayList<>(); // List of interceptors

    private static Character key = (char) -1;
    private static KeyProfile profile;

    /**
     * Adds an interceptor to the interceptors list.
     * @param interceptor An interceptor to register.
     */
    public static void addInterceptor(MenuKeyboardInterceptor interceptor) {
        if(interceptor != null)
            interceptors.add(interceptor);
    }

    /**
     * Removes an interceptor from the interceptors list.
     * @param interceptor The interceptor to remove.
     */
    public static void removeInterceptor(MenuKeyboardInterceptor interceptor) {
        if(interceptor != null)
            interceptors.remove(interceptor);
    }

    /**
     * Registers the next key press
     * and dispatches it to a menu
     * if necessary.
     */
    public static void poll() {
        if(!Keyboard.getEventKeyState()) { // Not a press event (ie. release)
            if(key != (char) -1)
                interceptors.forEach(e -> e.onKeyEvent(KeyboardEventType.KEY_RELEASE, key));
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
            interceptors.forEach(e -> e.onKeyEvent(KeyboardEventType.KEY_PRESS, key));
            return;
        }

        long currentTime = System.currentTimeMillis();
        long keyTime = profile.lastEventTime;

        if(profile.isTapped && currentTime - keyTime >= HOLD_TIME) {
            profile.lastEventTime = currentTime;
            profile.isTapped = false;
            interceptors.forEach(e -> e.onKeyEvent(KeyboardEventType.KEY_HOLD, key));
        }
        else if(!profile.isTapped && currentTime - keyTime >= CONSECUTIVE_HOLD_PRESS) {
            profile.lastEventTime = currentTime;
            interceptors.forEach(e -> e.onKeyEvent(KeyboardEventType.KEY_HOLD, key));
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
