package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.CharacterCell;
import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.GraphicalElement;
import com.magneticstudio.transience.ui.Sprite;
import com.magneticstudio.transience.util.Cache;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * This class represents all of the individual tiles
 * in this game.
 *
 * @author Max
 */
public class Tile implements Displayable {

    private GraphicalElement representation; // The graphical representation of the tile.
    private Entity entity; // The entity on this tile.

    private boolean traversable; // Whether an entity can be located on this tile from movement.

    /**
     * Creates a new, default, tile
     * object.
     */
    public Tile() throws SlickException {
        //representation = new Sprite(Cache.loadImage("resources/textures/tiles/def-tile-64.png"), 64, 64, 10);
        representation = new CharacterCell("Map Font", 'T');
    }

    /**
     * Gets the entity on this tile.
     * @return The entity on this tile.
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the entity on this tile.
     * @param entity The entity to be positioned on this tile.
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
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
        representation.render(graphics, x, y, centerSurround);
    }
}
