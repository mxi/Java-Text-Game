package com.magneticstudio.transience.ui;

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
