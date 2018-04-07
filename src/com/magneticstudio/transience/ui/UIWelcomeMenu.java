package com.magneticstudio.transience.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;

import java.awt.event.MouseEvent;

/**
 * This menu is the first menu encountered by the
 * user whenever they open the game.
 *
 * @author Max
 */
public class UIWelcomeMenu extends UIMenu {

    /**
     * Creates a new, default, UIWelcomeMenu
     * object.
     */
    public UIWelcomeMenu() {

    }

    /**
     * Processes keyboard input from the MenuKeyboard class
     * and its dispatch system.
     * @param type The type of event.
     * @param eventCharacter The key/character of the event.
     */
    @Override
    public void onKeyEvent(KeyboardEventType type, Character eventCharacter) {
        if(type == KeyboardEventType.KEY_PRESS && eventCharacter == 9) { // Toggle to next component.
            setFocusedComponentNext();
            return;
        }

        if(hasFocusedComponent() && (type == KeyboardEventType.KEY_PRESS || type == KeyboardEventType.KEY_HOLD) ) {
            getFocusedComponent().interpretKey(type, eventCharacter);
        }
    }

    /**
     * Processes mouse input from the MenuMouse class
     * and its dispatch system.
     * @param type The type of event.
     * @param eventButton The button of the event.
     */
    @Override
    public void onMouseEvent(MouseEventType type, Integer eventButton) {
        int mx = MenuMouse.getX() + getX();
        int my = MenuMouse.getY() + getY();

        if(!hasFocusedComponent() || focusedComponent != getComponentOn(mx, my)) {
            setFocusedComponentOn(mx, my);
            if(hasFocusedComponent())
                getFocusedComponent().interpretMouse(type, eventButton);
        }
        else if(focusedComponent == getComponentOn(mx, my)) {
            getFocusedComponent().interpretMouse(type, eventButton);
        }
        else {
            focusedComponent = -1;
        }
    }

    /**
     * Updates this menu object.
     */
    @Override
    public void update() {
        for(UIComponent c : componentList)
            c.update(this);
    }

    /**
     * Renders this menu onto the screen.
     * @param graphics The graphics object to use to render this menu.
     */
    @Override
    public void render(Graphics graphics) {
        for(UIComponent c : componentList)
            c.render(this, graphics);
    }
}
