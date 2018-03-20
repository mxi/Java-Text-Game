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

    private static final int PIXELS_PER_TILE = 64;
    private static final int CENTER_ADJUSTMENT = PIXELS_PER_TILE / 2;
    private static final int TILE_FONT_SIZE = 56;
    private static final float CANVAS_SCALE_MAX = 2f; // The maximum canvas scale.
    private static final float CANVAS_SCALE_MIN = .25f; // The minimum canvas scale.

    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.
    private EntityCollection entities = new EntityCollection(this); // The collection of entities.
    private FlowPosition position = new FlowPosition(); // The position of this tile set.
    private UnicodeFont font; // The font used to render the individual tiles.
    private TileSetGenerator generator; // The generator used to create this tile set.

    private Image canvas; // The image object used to render tile set on.
    private Graphics canvasGraphics; // The graphics object doing to the rendering.

    private float canvasScale = .5f; // The scale of the canvas.
    private float scaleMod = 0f; // The amount to modify the scale by.
    private int scaleModTimeRemain = 0; // The time remaining to scale the tile set.

     /**
      * Creates a new TileSet object with
      * the specified dimensions.
      * @param width The amount of tiles on the x axis.
      * @param height The amount of tiles on the y axis.
      */
    public TileSet(int width, int height) {
        font = Res.loadFont(
                "Consolas.ttf",
                Color.white,
                TILE_FONT_SIZE,
                Res.USE_DEFAULT,
                Res.USE_DEFAULT
        );
        tiles.setDimensions(width, height);
        tiles.fill(Tile.createVoidTile(this));
        tiles.setLocked(true);
        generator = null;
    }

    /**
     * Gets the scale of this tile set.
     * @return Tile set scale.
     */
    public float getScale() {
        return canvasScale;
    }

    /**
     * Sets the scale of this tile set.
     * @param nScale The new scale for this tile set.
     */
    public void setScale(float nScale) {
        canvasScale = Math.max(Math.min(CANVAS_SCALE_MAX, nScale), CANVAS_SCALE_MIN);
    }

    /**
     * Zooms in/out in the provided time.
     * @param nScale The new scale of the tile set.
     * @param time The time limit to reach the new scale.
     */
    public void setScale(float nScale, int time) {
        if(time <= 0 || nScale < CANVAS_SCALE_MIN || nScale > CANVAS_SCALE_MAX)
            return;
        scaleMod = (nScale - canvasScale) / time;
        scaleModTimeRemain = time;
    }

    // The limit on iterations for searching for a specific tile.
    private static final int RANDOM_POSITION_SEARCH_LIMIT = 250_000;

    /**
     * Gets a random position that is over a specific
     * tile type.
     * @param on The tile to get the random position on.
     * @return The location of the randomly selected tile.
     */
    public IntPoint randomPositionOn(Tile.Type on) {
        for(int i = 0; i < RANDOM_POSITION_SEARCH_LIMIT; i++) {
            int x = Game.rng.nextInt(tiles.getWidth());
            int y = Game.rng.nextInt(tiles.getHeight());
            Tile t = tiles.getElement(x, y);
            if(t != null && t.getTileType() == on)
                return new IntPoint(x, y);
        }
        return new IntPoint(0, 0);
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
        if(tiles.getElement(loc.x, loc.y) != null)
            tiles.getElement(loc.x, loc.y).setVisible(false);
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
        if(tiles.getElement(loc.x, loc.y) != null)
            tiles.getElement(loc.x, loc.y).setVisible(true);
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
     * Focuses on the specified tile.
     * @param x The X value of the location of the tile.
     * @param y The Y value of the location of the tile.
     */
    public void focusOn(int x, int y) {
        position.setTargetX( -x );
        position.setTargetY( -y );
    }

    /**
     * Adjusts the dimensions of graphical elements in
     * this tile set.
     */
    public void adjustGraphicalElements() {
        tiles.forEach(t -> t.getRepresentation().setDimensions(PIXELS_PER_TILE, PIXELS_PER_TILE));
        entities.forEach((f, e) -> e.getRepresentation().setDimensions(PIXELS_PER_TILE, PIXELS_PER_TILE));
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
     * Gets the pixel X of the center of the tile X
     * relative to the main rendering buffer (graphics
     * object passed to the render method in this tile set).
     * @param tileX The column number of the target tile to get render X of.
     * @return The pixel X of the center of the tile on the main rendering buffer.
     */
    private float getScreenTileRenderX(int tileX) {
        return getScreenCanvasRenderX() + (tileX * PIXELS_PER_TILE * canvasScale) + (CENTER_ADJUSTMENT * canvasScale);
    }

    /**
     * Gets the pixel Y of the center of the tile Y
     * relative to the main rendering buffer (graphics
     * object passed to the render method in this tile set).
     * @param tileY The row number of the target tile to get render Y of.
     * @return The pixel Y of the center of the tile on the main rendering buffer.
     */
    private float getScreenTileRenderY(int tileY) {
        return getScreenCanvasRenderY() + (tileY * PIXELS_PER_TILE * canvasScale) + (CENTER_ADJUSTMENT * canvasScale);
    }

    /**
     * Gets the pixel X of the center of
     * the tile X.
     * @param tileX The tile X (tile on which column)
     * @return The X value of the center of the tile when rendered onto the canvas.
     */
    private float getCanvasTileRenderX(int tileX) {
        return (tileX * PIXELS_PER_TILE * canvasScale) + (CENTER_ADJUSTMENT * canvasScale);
    }

    /**
     * Gets the pixel Y of the center of
     * the tile Y.
     * @param tileY The tile X (tile on which row)
     * @return The Y value of the center of the tile when rendered onto the canvas.
     */
    private float getCanvasTileRenderY(int tileY) {
        return (tileY * PIXELS_PER_TILE * canvasScale) + (CENTER_ADJUSTMENT * canvasScale);
    }

    /**
     * Gets the X value of the render position on screen
     * for the canvas of this tile set.
     * @return Render X value of tile set canvas.
     */
    private float getScreenCanvasRenderX() {
        return (Game.activeGame.getResolutionWidth() / 2) - (CENTER_ADJUSTMENT * canvasScale)
                + (position.getIntermediateX() * PIXELS_PER_TILE * canvasScale);
    }

    /**
     * Gets the Y value of the render position on screen
     * for the canvas of this tile set.
     * @return Render Y value of tile set canvas.
     */
    private float getScreenCanvasRenderY() {
        return (Game.activeGame.getResolutionHeight() / 2) - (CENTER_ADJUSTMENT * canvasScale)
                + (position.getIntermediateY() * PIXELS_PER_TILE * canvasScale);
    }

    /**
     * Renders the tile set onto the screen.
     * @param graphics The graphics object used to render anything on the main screen.
     */
    public void render(Graphics graphics) throws SlickException {
        if(canvas == null
           || canvas.getWidth() / PIXELS_PER_TILE != tiles.getWidth()
           || canvas.getHeight() / PIXELS_PER_TILE != tiles.getHeight()) {
            canvas = new Image(tiles.getWidth() * PIXELS_PER_TILE, tiles.getHeight() * PIXELS_PER_TILE);
            canvasGraphics = canvas.getGraphics();
        }

        canvasGraphics.clear();

        for(int iy = 0; iy < tiles.getHeight(); iy++) {
            if(!willTileBeVisibleY(iy))
                continue;

            float renderY = iy * PIXELS_PER_TILE;
            for(int ix = 0; ix < tiles.getWidth(); ix++) {
                if(!willTileBeVisibleX(ix))
                    continue;

                float renderX = ix * PIXELS_PER_TILE;
                tiles.getElement(ix, iy).render(
                    canvasGraphics,
                    renderX,
                    renderY,
                    true
                );

                //For debug:
                //canvasGraphics.setColor(Color.red);
                //canvasGraphics.drawRect(renderX, renderY, PIXELS_PER_TILE, PIXELS_PER_TILE);
            }
        }

        entities.forEach((f, e) -> {
            FlowPosition entPos = e.getPosition();
            float x = entPos.getIntermediateX() * PIXELS_PER_TILE;
            float y = entPos.getIntermediateY() * PIXELS_PER_TILE;
            canvasGraphics.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
            canvasGraphics.setColor(new Color(0f, 0f, 0f, 0f));
            canvasGraphics.fillRect(x, y, PIXELS_PER_TILE, PIXELS_PER_TILE);
            canvasGraphics.setDrawMode(Graphics.MODE_ADD);
            e.render(canvasGraphics, x, y, true);
        });

        graphics.drawImage(
            canvas.getScaledCopy(canvasScale),
            getScreenCanvasRenderX(),
            getScreenCanvasRenderY()
        );
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
        if(entities.getPlayer() != null) {
            FlowPosition playerPosition = entities.getPlayer().getPosition();
            focusOn(playerPosition.getTargetX(), playerPosition.getTargetY());
        }
        if(scaleModTimeRemain > 0) {
            canvasScale += scaleModTimeRemain - milliseconds < 0 ?
                scaleMod * scaleModTimeRemain : scaleMod * milliseconds;
            scaleModTimeRemain -= milliseconds;
        }
        position.update(milliseconds);
    }

    /**
     * Checks if the specified column would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param x The column number.
     * @return Whether that column would be visible or not.
     */
    private boolean willTileBeVisibleX(int x) {
        float translatedX = getScreenCanvasRenderX() + (x * PIXELS_PER_TILE * canvasScale);
        return translatedX > (-PIXELS_PER_TILE * canvasScale) && translatedX < Game.activeGame.getResolutionWidth();
    }

    /**
     * Checks if the specified row would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param y The row number.
     * @return Whether that row would be visible or not.
     */
    private boolean willTileBeVisibleY(int y) {
        float translatedY = getScreenCanvasRenderY() + (y * PIXELS_PER_TILE * canvasScale);
        return translatedY > (-PIXELS_PER_TILE * canvasScale) && translatedY < Game.activeGame.getResolutionHeight();
    }
}
