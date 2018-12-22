package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.GraphicalElement;
import org.newdawn.slick.Graphics;

/**
 * This class represents a single item
 * in the game.
 *
 * @author Max
 */
public class Item implements Displayable {

    private GraphicalElement representation; // The representation of this item.

    /**
     * Creates a new item object and uses the
     * specified representation for it.
     * @param representation The representation of this item.
     */
    public Item(GraphicalElement representation) {
        this.representation = representation;
    }

    /**
     * Renders this item onto the screen if applicable.
     * @param graphics The graphics used for drawing on main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        representation.render(graphics, x, y, centerSurround);
    }
}
