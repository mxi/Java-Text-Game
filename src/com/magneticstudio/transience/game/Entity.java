package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.GraphicalElement;
import com.magneticstudio.transience.ui.LogicalElement;
import org.newdawn.slick.Graphics;

/**
 * This class would represent an entity in the
 * game such as the character or an enemy.
 *
 * @author Max
 */
public abstract class Entity implements Displayable, LogicalElement {

    private GraphicalElement representation; // The representation of this entity.

    /**
     * Renders this entity onto the screen.
     * @param graphics The graphics object used to render anything on the main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        representation.render(graphics, x, y, centerSurround);
    }
}
