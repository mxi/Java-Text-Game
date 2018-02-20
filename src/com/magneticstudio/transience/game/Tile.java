package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.CharacterCell;
import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.GraphicalElement;
import com.magneticstudio.transience.util.Cache;
import org.newdawn.slick.Graphics;

/**
 * This class represents all of the individual tiles
 * in this game.
 *
 * @author Max
 */
public class Tile implements Displayable {

    private GraphicalElement representation; // The graphical representation of the tile.

    private boolean visible = true; // Whether this tile is visible (will be rendered).
    private boolean traversable = true; // Whether an entity can be located on this tile from movement.

    /**
     * Creates a new, default, tile
     * object.
     */
    public Tile() {
        representation = new CharacterCell(Cache.DEFAULT_FONT, 'T');
    }

    /**
     * Creates a new tile object.
     * @param font The name of the font to use for this tile.
     */
    public Tile(String font) {
        representation = new CharacterCell(font, 'T');
    }

    /**
     * Checks whether an entity can move onto
     * this tile.
     * @return Whether an entity can move onto this tile.
     */
    public boolean isTraversable() {
        return traversable;
    }

    /**
     * Sets whether entities can move onto this tile.
     * @param value Whether an entity can move onto this tile.
     */
    public void setTraversable(boolean value) {
        this.traversable = value;
    }

    /**
     * Checks whether this tile is being rendered onto
     * the screen.
     * @return Whether this tile is rendered onto the screen.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets whether this tile will be rendered
     * onto the screen.
     * @param visible Whether this tile will be rendered onto the screen.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
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
     * @param graphics The graphics object used to render anything on the main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        if(!visible)
            return;
        representation.render(graphics, x, y, centerSurround);
    }
}
