package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.ArrayList2D;
import com.magneticstudio.transience.util.IntDimension;
import org.newdawn.slick.Graphics;

/**
 * This class manages a 2d array of tiles.
 *
 * @author Max
 */
public class TileSet implements GraphicalElement {

    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.
    private int pixelsPerTile = 32; // Amount of pixels (width and height) a tile takes up.

    /**
     * Creates a new TileSet object with
     * the specified dimensions.
     * @param width The amount of tiles on the x axis.
     * @param height The amount of tiles on the y axis.
     */
    public TileSet(int width, int height) {
        tiles.setDimensions(width, height);
    }

    /**
     * Gets the amount of pixels (width and height)
     * each tile takes up.
     * @return Pixels per tile.
     */
    public int getPixelsPerTile() {
        return pixelsPerTile;
    }

    /**
     * Sets the amount of pixels (width and height)
     * each tile takes up.
     * @param pixelsPerTile Pixels per tile.
     */
    public void setPixelsPerTile(int pixelsPerTile) {
        this.pixelsPerTile = Math.min(Math.max(pixelsPerTile, 8), 128);
        tiles.forEach(e -> e.getRepresentation().setDimensions(this.pixelsPerTile, this.pixelsPerTile));
    }

    /**
     * Sets the alpha for all representations
     * of all tiles in this tile set.
     * @param alpha The alpha value for all tiles.
     */
    public void setAlpha(float alpha) {
        tiles.forEach(e -> e.getRepresentation().setAlpha(alpha));
    }

    /**
     * Gets the 2d array of tiles.
     * @return The 2d array of tiles.
     */
    public ArrayList2D<Tile> getTiles() {
        return tiles;
    }

    /**
     * Renders the tile set onto the screen.
     * @param graphics The graphics object to use for rendering.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        for(int iy = 0; iy < tiles.getHeight(); iy++) {
            for(int ix = 0; ix < tiles.getWidth(); ix++) {
                tiles.getElement(ix, iy).render(graphics, x + (pixelsPerTile * ix), y + (pixelsPerTile * iy), centerSurround);
            }
        }
    }
}
