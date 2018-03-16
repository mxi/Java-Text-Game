package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Game;
import com.magneticstudio.transience.ui.Res;
import com.magneticstudio.transience.ui.LogicalElement;
import com.magneticstudio.transience.util.ArrayList2D;
import com.magneticstudio.transience.util.FlowPosition;
import com.magneticstudio.transience.util.IntPoint;
import org.newdawn.slick.*;

/**
 * This class manages a 2d array of tiles.
 *
 * @author Max
 */
public class TileSet implements LogicalElement {

    private static final float FONT_PIXEL_RATIO = 12.5f / 11f;

    public static final int SMALL = 8; // The size of a small tile set (pixels per tile).
    public static final int MEDIUM = 24; // The size of medium tile set (pixels per tile).
    public static final int LARGE = 64; // The size of a large tile set (pixels per tile).

    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.
    private EntityCollection entities = new EntityCollection(); // The collection of entities.
    private FlowPosition position = new FlowPosition(); // The position of this tile set.
    private UnicodeFont font; // The font used to render the individual tiles.
    private TileSetGenerator generator; // The generator used to create this tile set.

    private int pixelsPerTile = 32; // Amount of pixels (width and height) a tile takes up.
    private float finePixelOffsetX = 0; // The fine pixel offset horizontally.
    private float finePixelOffsetY = 0; // The fine pixel offset vertically.

     /**
      * Creates a new TileSet object with
      * the specified dimensions.
      * @param ppt Specifies the pixels per tile.
      * @param width The amount of tiles on the x axis.
      * @param height The amount of tiles on the y axis.
      */
    public TileSet(int ppt, int width, int height) {
        pixelsPerTile = ppt < 8 ? 8 : ppt > 128 ? 128 : ppt;
        font = Res.loadFont("Consolas.ttf", Color.white, (int) (pixelsPerTile * FONT_PIXEL_RATIO), Res.USE_DEFAULT, Res.USE_DEFAULT);
        tiles.setDimensions(width, height);
        tiles.fill(Tile.createVoidTile(this));
        tiles.setLocked(true);
        entities.createPlayer(this);
        generator = null;
    }

    /**
     * Gets the generator that created this tile set.
     * @return The tile set generator.
     */
    public TileSetGenerator getGenerator() {
        return generator;
    }

    /**
     * Gets the collection of entities on this tile set.
     * @return The entity collection of this tile set.
     */
    public EntityCollection getEntities() {
        return entities;
    }

    /**
     * Hides the tile at the specified location.
     * @param x The X value of the tile's position.
     * @param y The Y value of the tile's position.
     */
    public void hideTile(int x, int y) {
        if(tiles.getElement(x, y) != null)
            tiles.getElement(x, y).setVisible(false);
    }

    /**
     * Hides the tile at the specified location.
     * @param loc Location of the tile to hide.
     */
    public void hideTile(IntPoint loc) {
        if(tiles.getElement(loc.getX(), loc.getY()) != null)
            tiles.getElement(loc.getX(), loc.getY()).setVisible(false);
    }

    /**
     * Sets the specified tile to be shown.
     * @param x The X value of the tile's position.
     * @param y The Y value of the tile's position.
     */
    public void showTile(int x, int y) {
        if(tiles.getElement(x, y) != null)
            tiles.getElement(x, y).setVisible(true);
    }

    /**
     * Sets the specified tile to be shown.
     * @param loc Location of the tile to show.
     */
    public void showTile(IntPoint loc) {
        if(tiles.getElement(loc.getX(), loc.getY()) != null)
            tiles.getElement(loc.getX(), loc.getY()).setVisible(true);
    }

    /**
     * Gets the FlowPosition object that controls the
     * position of this TileSet on screen.
     * @return The FlowPosition object controlling position/animation of this TileSet.
     */
    public FlowPosition getPosition() {
        return position;
    }

    /**
     * Centers on the specified tile.
     * @param x The X value of the location of the tile.
     * @param y The Y value of the location of the tile.
     */
    public void centerOn(int x, int y) {
        position.setTargetX( -Game.activeGame.getResolutionWidth() / pixelsPerTile / 2 + x );
        position.setTargetY( -Game.activeGame.getResolutionHeight() / pixelsPerTile / 2 + y );
    }

    /**
     * Creates fine adjustments to perfectly
     * center each tile with its designated
     * square; location.
     */
    public void setPixelPerfect() {
        finePixelOffsetX = Game.activeGame.getResolutionWidth() % pixelsPerTile / 2;
        if (Game.activeGame.getResolutionWidth() / pixelsPerTile % 2 == 0)
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
        font = Res.modifyFont(font, null, (int) (this.pixelsPerTile * FONT_PIXEL_RATIO), Res.USE_DEFAULT, Res.USE_DEFAULT);
        tiles.forEach(t -> {
            t.getRepresentation().setDimensions(this.pixelsPerTile, this.pixelsPerTile);
            t.setFont(font);
        });
        entities.forEach((f, e) -> e.getRepresentation().setDimensions(this.pixelsPerTile, this.pixelsPerTile));
    }

    /**
     * Gets the font used by all tiles in
     * this tile set.
     * @return Font used by all tiles in the tile set.
     */
    public UnicodeFont getFont() {
        return font;
    }

    /**
     * Gets the font size for the current
     * dimensions of each tile of the tile set.
     * @return The font size for each tile.
     */
    public int getFontSize() {
        return pixelsPerTile * 10 / 11;
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
     * Gets the X value of the position of
     * the tile set to be rendered on.
     * @param baseX The base X of a tile (location in the tiles array list)
     * @return The render X value.
     */
    private float getRenderX(float baseX) {
        return (pixelsPerTile * baseX) - (position.getIntermediateX() * pixelsPerTile) + finePixelOffsetX;
    }

    /**
     * Gets the Y value of the position of
     * the tile set to be rendered on.
     * @param baseY The base Y of a tile (location in the tiles array list)
     * @return The render Y value.
     */
    private float getRenderY(float baseY) {
        return (pixelsPerTile * baseY) - (position.getIntermediateY() * pixelsPerTile) + finePixelOffsetY;
    }

    private Image canvas; // The image object used to render tile set on.
    private Graphics canvasGraphics; // The graphics object doing to the rendering.

    /**
     * Renders the tile set onto the screen.
     * @param graphics The graphics object used to render anything on the main screen.
     */
    public void render(Graphics graphics) throws SlickException {
        if(canvas == null) {
            canvas = new Image(Game.activeGame.getResolutionWidth(), Game.activeGame.getResolutionHeight());
            canvasGraphics = canvas.getGraphics();
        }

        canvasGraphics.clear();

        for(int iy = 0; iy < tiles.getHeight(); iy++) {
            if(!willTileBeVisibleY(iy))
                continue;

            float renderY = getRenderY(iy);
            for(int ix = 0; ix < tiles.getWidth(); ix++) {
                if(!willTileBeVisibleX(ix))
                    continue;

                float renderX = getRenderX(ix);
                tiles.getElement(ix, iy).render(
                        canvasGraphics,
                        renderX,
                        renderY,
                        false
                );
            }
        }

        entities.forEach((f, e) -> {
            FlowPosition entPos = e.getPosition();
            float x = getRenderX(entPos.getIntermediateX());
            float y = getRenderY(entPos.getIntermediateY());
            canvasGraphics.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
            canvasGraphics.setColor(new Color(0f, 0f, 0f, 0f));
            canvasGraphics.fillRect(x, y, pixelsPerTile, pixelsPerTile);
            canvasGraphics.setDrawMode(Graphics.MODE_ADD);
            e.render(canvasGraphics, x, y, false);
        });

        graphics.drawImage(canvas, 0, 0);
    }

    /**
     * Updates this tile set, so when the player moves within
     * the game, the tile set will center around the player.
     * @param milliseconds The elapsed time since last update.
     */
    @Override
    public void update(int milliseconds) {
        entities.forEach((f, e) -> {
            e.entityUpdate(this, milliseconds);
            e.updatePosition(milliseconds);
        });
        FlowPosition playerPosition = entities.getPlayer().getPosition();
        centerOn(playerPosition.getTargetX(), playerPosition.getTargetY());
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
