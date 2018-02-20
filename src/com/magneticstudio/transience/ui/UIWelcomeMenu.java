package com.magneticstudio.transience.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;

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
        if(type == KeyboardEventType.KEY_PRESS)
            System.out.println(eventCharacter);
    }

    /**
     * Processes mouse input from the MenuMouse class
     * and its dispatch system.
     * @param type The type of event.
     * @param eventButton The button of the event.
     */
    @Override
    public void onMouseEvent(MouseEventType type, Integer eventButton) {
        if(type == MouseEventType.BUTTON_PRESS)
            setFocusedComponentOn(Mouse.getX(), Mouse.getY());
    }

    /**
     * Updates this menu object.
     */
    @Override
    public void update() {
        for(UIComponent c : componentList)
            c.update();
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
