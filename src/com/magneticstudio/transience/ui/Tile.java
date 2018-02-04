package com.magneticstudio.transience.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This class represents all of the individual tiles
 * in this game.
 *
 * @author Max
 */
public class Tile implements GraphicalElement {

    private Sprite representation; // The graphical representation of the tile.

    /**
     * Creates a new, default, tile
     * object.
     */
    public Tile() throws SlickException {
        representation = new Sprite(new Image("resources/textures/tiles/def-tile-64.png"), 64, 64);
        representation.setFrameRate(10);
    }

    /**
     * Gets the representation of this tile.
     * @return Representation of this tile.
     */
    public Sprite getRepresentation() {
        return representation;
    }

    /**
     * Renders this tile onto the screen.
     * @param graphics The graphics object to use for rendering.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        representation.render(graphics, x, y, centerSurround);
    }
}
