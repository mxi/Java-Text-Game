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
     * Renders this tile onto the screen.
     * @param graphics The graphics object to use for rendering.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     */
    @Override
    public void render(Graphics graphics, float x, float y) {
        representation.render(graphics, x, y);
    }
}
