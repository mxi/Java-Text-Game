package com.magneticstudio.transience.ui;

import org.newdawn.slick.Graphics;

/**
 * This interface simply specifies one
 * function that needs to implemented by
 * any class that wishes to be rendered
 * onto the screen.
 *
 * @author Max
 */
public interface GraphicalElement {

    /**
     * Renders this object onto the screen.
     * @param graphics The graphics object to use for rendering.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     */
    void render(Graphics graphics, float x, float y);
}
