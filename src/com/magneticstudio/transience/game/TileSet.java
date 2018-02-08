package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.Game;
import com.magneticstudio.transience.ui.LogicalElement;
import com.magneticstudio.transience.util.ArrayList2D;
import com.magneticstudio.transience.util.FlowPosition;
import org.newdawn.slick.Graphics;

/**
 * This class manages a 2d array of tiles.
 *
 * @author Max
 */
public class TileSet implements Displayable, LogicalElement {

    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.

    // --- GRAPHICS/DISPLAY VARIABLES:
    private FlowPosition position = new FlowPosition(); // The position of this tile set.
    private int pixelsPerTile = 32; // Amount of pixels (width and height) a tile takes up.

    private float finePixelOffsetX = 0; // The fine pixel offset horizontally.
    private float finePixelOffsetY = 0; // The fine pixel offset vertically.

    // --- GAME-PLAY


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
     * Gets the FlowPosition object of this TileSet.
     * @return FlowPosition of this TileSet.
     */
    public FlowPosition getPosition() {
        return position;
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
     * Creates fine adjustments to perfectly
     * center each tile with its designated
     * square; location.
     */
    public void setPixelPerfect() {
        finePixelOffsetX = Game.activeGame.getResolutionWidth() % pixelsPerTile / 2 + 1;
        if(Game.activeGame.getResolutionWidth() / pixelsPerTile % 2 == 0)
            finePixelOffsetX -= (pixelsPerTile / 2);

        finePixelOffsetY = (Game.activeGame.getResolutionHeight() / 2) -
                ((Game.activeGame.getResolutionHeight() / 2 / pixelsPerTile) + 1) * pixelsPerTile - (pixelsPerTile / 2) + pixelsPerTile;
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
     * @param graphics The graphics object used to render anything on the main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     *                       (doesn't apply to this object)
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        for(int iy = 0; iy < tiles.getHeight(); iy++) {
            if(!willTileBeVisibleY(iy))
                continue;
            for(int ix = 0; ix < tiles.getWidth(); ix++) {
                if(!willTileBeVisibleX(ix))
                    continue;
                tiles.getElement(ix, iy).render(
                        graphics,
                        x + (pixelsPerTile * ix) - position.getIntermediateX() * pixelsPerTile + finePixelOffsetX,
                        y + (pixelsPerTile * iy) - position.getIntermediateY() * pixelsPerTile + finePixelOffsetY,
                        false
                );
            }
        }
    }

    /**
     * Updates this tile set, so when the player moves within
     * the game, the tile set will center around the player.
     * @param milliseconds The elapsed time since last update.
     */
    @Override
    public void update(int milliseconds) {
        position.update(milliseconds);
    }

    /**
     * Checks if the specified column would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param x The column number.
     * @return Whether that column would be visible or not.
     */
    private boolean willTileBeVisibleX(int x) {
        return (x - position.getIntermediateX()) * pixelsPerTile + finePixelOffsetX >= -pixelsPerTile
                && (x - position.getIntermediateX()) * pixelsPerTile + finePixelOffsetX < Game.activeGame.getResolutionWidth();
    }

    /**
     * Checks if the specified row would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param y The row number.
     * @return Whether that row would be visible or not.
     */
    private boolean willTileBeVisibleY(int y) {
        return (y - position.getIntermediateY()) * pixelsPerTile + finePixelOffsetY >= -pixelsPerTile
                && (y - position.getIntermediateY()) * pixelsPerTile + finePixelOffsetY < Game.activeGame.getResolutionHeight();
    }
}
