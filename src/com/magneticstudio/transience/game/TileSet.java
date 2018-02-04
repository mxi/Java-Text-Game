package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.Game;
import com.magneticstudio.transience.ui.LogicalElement;
import com.magneticstudio.transience.util.ArrayList2D;
import com.magneticstudio.transience.util.IntPoint;

/**
 * This class manages a 2d array of tiles.
 *
 * @author Max
 */
public class TileSet implements Displayable, LogicalElement {

    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.
    private int pixelsPerTile = 32; // Amount of pixels (width and height) a tile takes up.

    private int tileOffsetX = 0; // The tile offset horizontally.
    private int tileOffsetY = 0; // The tile offset vertically.
    private int animationTimeMillis = 150; // Amount of time to transition to new offset.

    private float pixelOffsetX = 0; // The current pixel offset horizontally.
    private float pixelOffsetY = 0; // The current pixel offset vertically.

    private float pixelMovementX = 0; // The amount to update pixelOffsetX by.
    private float pixelMovementY = 0; // The amount to update pixelOffsetY by.

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
     * Gets the animation time for
     * movement of tileset in milliseconds.
     * @return Milliseconds for movement animation.
     */
    public int getAnimationTime() {
        return animationTimeMillis;
    }

    /**
     * Sets the new animation time
     * for the movement of the tile set
     * in milliseconds.
     * @param newTime New movement time in milliseconds for tile set animation.
     */
    public void setAnimationTimeMillis(int newTime) {
        animationTimeMillis = Math.max(Math.min(newTime, 500), 1);
    }

    /**
     * Sets the tile offsets using an IntPoint
     * object.
     * @param offset The point containing horizontal and vertical offset.
     */
    public void setTileOffset(IntPoint offset) {
        setHorizontalTileOffset(offset.getX());
        setVerticalTileOffset(offset.getY());
    }

    /**
     * Sets the tile offsets.
     * @param x The amount of tiles to move horizontally.
     * @param y The amount of tiles to move vertically.
     */
    public void setTileOffset(int x, int y) {
        setHorizontalTileOffset(x);
        setVerticalTileOffset(y);
    }

    /**
     * Gets the tile offset as an IntPoint object.
     * @return Tile offset as an IntPoint object.
     */
    public IntPoint getTileOffset() {
        return new IntPoint(tileOffsetX, tileOffsetY);
    }

    /**
     * Gets the horizontal tile offset.
     * @return Horizontal tile offset.
     */
    public int getHorizontalTileOffset() {
        return tileOffsetX;
    }

    /**
     * Sets the tile offset horizontally.
     * @param tileOffsetHorizontal New horizontal offset.
     */
    public void setHorizontalTileOffset(int tileOffsetHorizontal) {
        pixelMovementX = (tileOffsetHorizontal * pixelsPerTile - pixelOffsetX) / animationTimeMillis;
        tileOffsetX = tileOffsetHorizontal;
    }

    /**
     * Gets the vertical tile offset.
     * @return vertical tile offset.
     */
    public int getVerticalTileOffset() {
        return tileOffsetY;
    }

    /**
     * Sets the tile offset vertically.
     * @param tileOffsetVertically New horizontal offset.
     */
    public void setVerticalTileOffset(int tileOffsetVertically) {
        pixelMovementY = (tileOffsetVertically * pixelsPerTile - pixelOffsetY) / animationTimeMillis;
        tileOffsetY = tileOffsetVertically;
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
     * Sets the pixels per tile based on how many tiles
     * should fit in the window's width.
     * @param amount Amount of tiles to fit in a row.
     */
    public void setTileCountHorizontally(int amount) {
        setPixelsPerTile(Game.activeGame.getResolutionWidth() / amount);
    }

    /**
     * Sets the pixels per tile based on how many tiles
     * should fit in the window's height.
     * @param amount Amount of tiles to fit in a column.
     */
    public void setTileCountVertically(int amount) {
        setPixelsPerTile(Game.activeGame.getResolutionHeight() / amount);
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
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(float x, float y, boolean centerSurround) {
        for(int iy = 0; iy < tiles.getHeight(); iy++) {
            for(int ix = 0; ix < tiles.getWidth(); ix++) {
                if(!willTileBeVisible(ix, iy))
                    continue;
                tiles.getElement(ix, iy).render(x + (pixelsPerTile * ix) - pixelOffsetX,
                        y + (pixelsPerTile * iy) + pixelOffsetY, centerSurround);
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
        if((int) pixelOffsetX != tileOffsetX * pixelsPerTile)
            pixelOffsetX += pixelMovementX * milliseconds;

        if((int) pixelOffsetY != tileOffsetY * pixelsPerTile)
            pixelOffsetY += pixelMovementY * milliseconds;
    }

    /**
     * Checks to see if the a rendered tile at that
     * location would be visible, and if not, the
     * render function won't bother to render that tile.
     * @param x The x location of that tile.
     * @param y The y location of that tile.
     * @return Whether the tile will be visible.
     */
    private boolean willTileBeVisible(int x, int y) {
        return true;
    }
}
