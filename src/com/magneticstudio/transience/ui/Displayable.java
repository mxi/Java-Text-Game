package com.magneticstudio.transience.ui;

import org.newdawn.slick.Graphics;

/**
 * This interface simply defines one function
 * that may be implemented by any class that
 * wishes to only be rendered onto the screen
 * without having to implement any other functions
 * that are specified in the GraphicalElement interface.
 *
 * @author Max
 */
public interface Displayable {

    /**
     * Renders this object onto the screen in
     * a rendering context.
     * @param graphics The graphics used for drawing on main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    void render(Graphics graphics, float x, float y, boolean centerSurround);
}
