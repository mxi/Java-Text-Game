package com.magneticstudio.transience.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages inputs from the Mouse
 * and dispatches them to any interceptors.
 *
 * @author Max
 */
public class MenuMouse {

    private static final List<MenuMouseInterceptor> interceptors = new ArrayList<>(); // List of interceptors

    private static boolean isLeftMouseDown = false; // Tells whether the left mouse button is down.
    private static boolean isRightMouseDown = false; // Tells whether the right mouse button is down.

    /**
     * Adds an interceptor to the interceptors list.
     * @param interceptor An interceptor to register.
     */
    public static void addInterceptor(MenuMouseInterceptor interceptor) {
        if(interceptor != null)
            interceptors.add(interceptor);
    }

    /**
     * Removes an interceptor from the interceptors list.
     * @param interceptor The interceptor to remove.
     */
    public static void removeInterceptor(MenuMouseInterceptor interceptor) {
        if(interceptor != null)
            interceptors.remove(interceptor);
    }

    /**
     * Polls any current mouse events
     * and dispatches the necessary events.
     */
    public static void poll() {
        if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON) && !isLeftMouseDown) {
            interceptors.forEach(e -> e.onMouseEvent(MouseEventType.BUTTON_PRESS, Input.MOUSE_LEFT_BUTTON));
            isLeftMouseDown = true;
        }
        else if(!Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON) && isLeftMouseDown) {
            interceptors.forEach(e -> e.onMouseEvent(MouseEventType.BUTTON_RELEASE, Input.MOUSE_LEFT_BUTTON));
            isLeftMouseDown = false;
        }

        if(Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON) && !isRightMouseDown) {
            interceptors.forEach(e -> e.onMouseEvent(MouseEventType.BUTTON_PRESS, Input.MOUSE_RIGHT_BUTTON));
            isRightMouseDown = true;
        }
        else if(!Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON) && isRightMouseDown) {
            interceptors.forEach(e -> e.onMouseEvent(MouseEventType.BUTTON_RELEASE, Input.MOUSE_RIGHT_BUTTON));
            isRightMouseDown = false;
        }
    }
}
