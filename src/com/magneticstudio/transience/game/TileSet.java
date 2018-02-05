package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.Game;
import com.magneticstudio.transience.ui.LogicalElement;
import com.magneticstudio.transience.util.ArrayList2D;
import com.magneticstudio.transience.util.IntPoint;
import org.newdawn.slick.Graphics;

/**
 * This class manages a 2d array of tiles.
 *
 * @author Max
 */
public class TileSet implements Displayable, LogicalElement {

    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.

    // --- GRAPHICS/DISPLAY VARIABLES:
    private int pixelsPerTile = 32; // Amount of pixels (width and height) a tile takes up.
    private int animationTimeMillis = 150; // Amount of time to transition to new offset.

    private int tileOffsetX = 0; // The tile offset horizontally.
    private int tileOffsetY = 0; // The tile offset vertically.

    private float pixelOffsetX = 0; // The current pixel offset horizontally.
    private float pixelOffsetY = 0; // The current pixel offset vertically.

    private float finePixelOffsetX = 0; // A fine adjustment of the horizontal alignment.
    private float finePixelOffsetY = 0; // A fine adjustment of the vertical alignment.

    private float pixelMovementX = 0; // The amount to update pixelOffsetX by.
    private float pixelMovementY = 0; // The amount to update pixelOffsetY by.

    private boolean isHorizontalMovementLeft = false; // Tells whether the horizontal movement is going left.
    private boolean isVerticalMovementUp = false; // Tells whether the vertical movement is up.

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
        pixelMovementX = -(tileOffsetHorizontal - tileOffsetX) * pixelsPerTile / (float) animationTimeMillis;
        tileOffsetX = tileOffsetHorizontal;
        isHorizontalMovementLeft = pixelMovementX > 0;
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
        pixelMovementY = (tileOffsetVertically - tileOffsetY) * pixelsPerTile / (float) animationTimeMillis;
        tileOffsetY = tileOffsetVertically;
        isVerticalMovementUp = pixelMovementY < 0;
    }

    /**
     * Centers on the specified tile.
     * @param x The X value of the location of the tile.
     * @param y The Y value of the location of the tile.
     */
    public void centerOn(int x, int y) {
        setHorizontalTileOffset( -Game.activeGame.getResolutionWidth() / pixelsPerTile / 2 + x );
        setVerticalTileOffset( -Game.activeGame.getResolutionHeight() / pixelsPerTile / 2 + y );
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
                tiles.getElement(ix, iy).render(graphics, x + (pixelsPerTile * ix) - pixelOffsetX + finePixelOffsetX,
                        y + (pixelsPerTile * iy) - pixelOffsetY + finePixelOffsetY, false);
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
        if( (pixelOffsetX > tileOffsetX * pixelsPerTile && isHorizontalMovementLeft)
                || (pixelOffsetX < tileOffsetX * pixelsPerTile && !isHorizontalMovementLeft) )
            pixelOffsetX -= pixelMovementX * milliseconds;
        else
            pixelOffsetX = tileOffsetX * pixelsPerTile;

        if( (pixelOffsetY > tileOffsetY * pixelsPerTile && isVerticalMovementUp)
                || (pixelOffsetY < tileOffsetY * pixelsPerTile && !isVerticalMovementUp) )
            pixelOffsetY += pixelMovementY * milliseconds;
        else
            pixelOffsetY = tileOffsetY * pixelsPerTile;
    }

    /**
     * Checks if the specified column would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param x The column number.
     * @return Whether that column would be visible or not.
     */
    private boolean willTileBeVisibleX(int x) {
        return (x - tileOffsetX) * pixelsPerTile + finePixelOffsetX >= -pixelsPerTile
                && (x - tileOffsetX) * pixelsPerTile + finePixelOffsetX < Game.activeGame.getResolutionWidth();
    }

    /**
     * Checks if the specified row would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param y The row number.
     * @return Whether that row would be visible or not.
     */
    private boolean willTileBeVisibleY(int y) {
        return (y - tileOffsetY) * pixelsPerTile + finePixelOffsetY >= -pixelsPerTile
                && (y - tileOffsetY) * pixelsPerTile + finePixelOffsetY < Game.activeGame.getResolutionHeight();
    }
}
