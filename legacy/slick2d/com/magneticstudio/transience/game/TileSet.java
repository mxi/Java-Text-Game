package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Game;
import com.magneticstudio.transience.ui.Res;
import com.magneticstudio.transience.ui.LogicalElement;
import com.magneticstudio.transience.util.*;
import org.newdawn.slick.*;

/**
 * This class manages a 2d array of tiles.
 *
 * @author Max
 */
public class TileSet implements LogicalElement {

    public static final int PIXELS_PER_TILE = 64;
    public static final int CENTER_ADJUSTMENT = PIXELS_PER_TILE / 2;
    public static final int TILE_FONT_SIZE = 56;

    private static final float CANVAS_SCALE_MAX = 1f; // The maximum canvas scale.
    private static final float CANVAS_SCALE_MIN = .01f; // The minimum canvas scale.

    // Anything related to the current tile set (the object; not static things) below
    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.
    private FlowPosition position = new FlowPosition(); // The position of this tile set.

    private Environment environment = new Environment(); // The environment for this tile set.
    private EntityCollection entities = new EntityCollection(); // The collection of entities.

    private TileSetGenerator generator; // The generator used to create this tile set.
    private UnicodeFont font; // The font used to render the individual tiles.

    // Graphics
    private Image canvas; // The image object used to render tile set on.
    private Graphics canvasGraphics; // The graphics object doing to the rendering.

    private Shake shaker; // The shaker for the tile set.

    private float canvasScale = 1f; // The scale of the canvas.
    private float scaleMod = 0f; // The amount to modify the scale by.
    private int scaleModTimeRemain = 0; // The time remaining to scale the tile set.

    // Game/ai
    private boolean runAi = false; // Whether to run the Ai on the next update.

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
        shaker = new Shake();
        generator = null;
    }

    /**
     * Gets the object responsible for shaking this tile set.
     * @return The shaker.
     */
    public Shake getShaker() {
        return shaker;
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
     * Gets the pixels per individual tile.
     * @return Pixels per individual tile.
     */
    public int getPixelsPerTile() {
        return (int) (PIXELS_PER_TILE * canvasScale);
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
    @Deprecated
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
     * Sets the generator.
     * @param generator The generator used to create this tile set.
     */
    public void setGenerator(TileSetGenerator generator) {
        this.generator = generator;
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
        entities.forEach(e -> e.getRepresentation().setDimensions(PIXELS_PER_TILE, PIXELS_PER_TILE));
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
     * Gets the environment of this tile set.
     * @return The tile set environment.
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Tells the entities in the entity collection
     * to perform some action on next update.
     */
    public void runAi() {
        runAi = true;
    }

    /**
     * Gets the X value of the render position on screen
     * for the canvas of this tile set.
     * @return Render X value of tile set canvas.
     */
    public float getCanvasX() {
        return (Game.activeGame.getResolutionWidth() / 2) - (CENTER_ADJUSTMENT * canvasScale)
                + (position.getIntermediateX() * PIXELS_PER_TILE * canvasScale);
    }

    /**
     * Gets the Y value of the render position on screen
     * for the canvas of this tile set.
     * @return Render Y value of tile set canvas.
     */
    public float getCanvasY() {
        return (Game.activeGame.getResolutionHeight() / 2) - (CENTER_ADJUSTMENT * canvasScale)
                + (position.getIntermediateY() * PIXELS_PER_TILE * canvasScale);
    }

    /**
     * Gets the x value of the location of a tile on the canvas.
     * @param tileX The column of tiles referring to.
     * @return The x value of the location of a tile on the canvas.
     */
    public int tileToCanvasX(int tileX) {
        return (tileX * PIXELS_PER_TILE);
    }

    /**
     * Gets the y value of the location of a tile on the canvas.
     * @param tileY The column of tiles referring to.
     * @return The y value of the location of a tile on the canvas.
     */
    public int tileToCanvasY(int tileY) {
        return (tileY * PIXELS_PER_TILE);
    }

    /**
     * Gets the location of a tile on the canvas.
     * @param tileX X value of the tile.
     * @param tileY Y value of the tile.
     * @return The location of the tile on the canvas.
     */
    public IntPoint tileToCanvasCoordinates(int tileX, int tileY) {
        return new IntPoint(
            tileToCanvasX(tileX),
            tileToCanvasY(tileY)
        );
    }

    /**
     * Gets the pixel's x on which the column of tiles lie on the screen.
     * @param tileX The column to get the pixel x of.
     * @return The x location of the tile on the screen.
     */
    public float tileToDisplayLocationX(int tileX) {
        return getCanvasX() + (tileX * PIXELS_PER_TILE * canvasScale) + shaker.getHorizontalOffset();
    }

    /**
     * Gets the pixel's y on which the row of tiles lie on the screen.
     * @param tileY The row to get the pixel y of.
     * @return The y location of the tile on the screen.
     */
    public float tileToDisplayLocationY(int tileY) {
        return getCanvasY() + (tileY * PIXELS_PER_TILE * canvasScale) + shaker.getVerticalOffset();
    }

    /**
     * Gets the location of the pixel a tile will be rendered on
     * (upper left corner).
     * @param tileX The tile located on this x.
     * @param tileY The tile located on this y.
     * @return The pixel on which the tile is rendered on.
     */
    public FloatPoint tileToDisplayCoordinates(int tileX, int tileY) {
        return new FloatPoint(
            tileToDisplayLocationX(tileX),
            tileToDisplayLocationY(tileY)
        );
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

                Tile t = tiles.getElement(ix, iy);
                if(t.getTileType() == Tile.Type.VOID)
                    continue;

                float renderX = ix * PIXELS_PER_TILE;
                t.render(
                    canvasGraphics,
                    renderX,
                    renderY,
                    false
                );
            }
        }

        entities.forEach(e -> {
            FlowPosition entPos = e.getPosition();
            float x = entPos.getIntermediateX() * PIXELS_PER_TILE;
            float y = entPos.getIntermediateY() * PIXELS_PER_TILE;
            canvasGraphics.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
            canvasGraphics.setColor(new Color(0f, 0f, 0f, 0f));
            canvasGraphics.fillRect(x, y, PIXELS_PER_TILE, PIXELS_PER_TILE);
            canvasGraphics.setDrawMode(Graphics.MODE_ADD);
            e.render(canvasGraphics, x, y, false);
        });

        Image preprocessedCanvas = canvas.getScaledCopy(canvasScale);
        if(entities.getPlayer() != null) {
            FlowPosition playerPos = entities.getPlayer().getPosition();
            IntPoint playerLoc = tileToCanvasCoordinates(playerPos.getTargetX(), playerPos.getTargetY());
            preprocessedCanvas.setCenterOfRotation(playerLoc.x * canvasScale, playerLoc.y * canvasScale);
        }
        preprocessedCanvas.rotate(shaker.getRotationOffset());

        graphics.drawImage(
            preprocessedCanvas,
            getCanvasX() + shaker.getHorizontalOffset(),
            getCanvasY() + shaker.getVerticalOffset()
        );
        environment.render(this, graphics);
    }

    /**
     * This function renders things on top
     * of what the main Game class renders.
     * @param graphics The graphics from Slick2d.
     */
    public void postRender(Graphics graphics) {
        if(entities.getPlayer() != null)
            entities.getPlayer().renderHud(graphics);
    }

    /**
     * Updates this tile set, so when the player moves within
     * the game, the tile set will center around the player.
     * @param milliseconds The elapsed time since last update.
     */
    @Override
    public void update(int milliseconds) {
        // Entity updates.
        entities.forEach(e -> {
            e.updatePosition(milliseconds);
            if(e instanceof Enemy) {
                Enemy cast = (Enemy) e;
                if(runAi)
                    cast.runAi(this);

            }
            else {
                ((Player)e).update(this);
            }
        });

        if(entities.getPlayer() != null) {
            FlowPosition playerPosition = entities.getPlayer().getPosition();
            focusOn(playerPosition.getTargetX(), playerPosition.getTargetY());
        }

        runAi = false;

        // Scaling and transformations:
        if(scaleModTimeRemain > 0) {
            canvasScale += scaleModTimeRemain - milliseconds < 0 ? scaleMod * scaleModTimeRemain : scaleMod * milliseconds;
            scaleModTimeRemain -= milliseconds;
        }
        shaker.update(milliseconds);
        position.update(milliseconds);
    }

    /**
     * Checks if the specified column would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param x The column number.
     * @return Whether that column would be visible or not.
     */
    private boolean willTileBeVisibleX(int x) {
        float tileX = tileToDisplayLocationX(x);
        return tileX > (-PIXELS_PER_TILE * canvasScale) && tileX < Game.activeGame.getResolutionWidth();
    }

    /**
     * Checks if the specified row would be visible on the screen;
     * the tile itself isn't out of bounds of the rendering window.
     * @param y The row number.
     * @return Whether that row would be visible or not.
     */
    private boolean willTileBeVisibleY(int y) {
        float tileY = tileToDisplayLocationY(y);
        return tileY > (-PIXELS_PER_TILE * canvasScale) && tileY < Game.activeGame.getResolutionHeight();
    }
}
