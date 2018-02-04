package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.GraphicalElement;
import com.magneticstudio.transience.ui.Sprite;
import com.magneticstudio.transience.util.Cache;
import org.newdawn.slick.SlickException;

/**
 * This class represents all of the individual tiles
 * in this game.
 *
 * @author Max
 */
public class Tile implements Displayable {

    private GraphicalElement representation; // The graphical representation of the tile.

    /**
     * Creates a new, default, tile
     * object.
     */
    public Tile() throws SlickException {
        representation = new Sprite(Cache.loadImage("resources/textures/tiles/def-tile-64.png"), 64, 64, 10);
    }

    /**
     * Gets the representation of this tile.
     * @return Representation of this tile.
     */
    public GraphicalElement getRepresentation() {
        return representation;
    }

    /**
     * Renders this tile onto the screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(float x, float y, boolean centerSurround) {
        representation.render(x, y, centerSurround);
    }
}
