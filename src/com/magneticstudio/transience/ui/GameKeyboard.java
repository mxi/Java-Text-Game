package com.magneticstudio.transience.ui;

import org.lwjgl.input.Keyboard;

/**
 * This class manages the keyboard events that
 * are specified for this game. No key will
 * be able to be held, and each movement by the player
 * must be done through releasing and pressing the key.
 *
 * @author Max
 */
public class GameKeyboard {

    public static final int KEY_COOLDOWN_TIME = 100; // Time in milliseconds before another key can be pressed.
    private static int KEY = -1; // The key that has triggered the latest event.
    private static boolean isPressed = false; // Whether the 'key' is pressed.
    private static long lastEventTime = System.currentTimeMillis(); // The time of the last key ever pressed.

    /**
     * Registers the next key for the game.
     */
    public static void poll() {
        if(!Keyboard.getEventKeyState()) { // Not a press event (ie. release)
            KEY = -1;
            return;
        }

        isPressed = false;
        long currentTime = System.currentTimeMillis();
        int eventKey = Keyboard.getEventKey();
        if(KEY != eventKey && currentTime - lastEventTime >= KEY_COOLDOWN_TIME) {
            KEY = eventKey;
            lastEventTime = System.currentTimeMillis();
            isPressed = true;
        }
    }

    /**
     * Checks whether the specified key is the
     * one that is tapped.
     * @param key Key to check if tapped.
     * @return Whether the key is tapped.
     */
    @Deprecated
    public static boolean isTapped(Integer key) {
        return isPressed && KEY == key;
    }

    /**
     * Gets the tapped key.
     * @return The tapped key.
     */
    public static int getTappedKey() {
        return isPressed ? KEY : -1;
    }
}
