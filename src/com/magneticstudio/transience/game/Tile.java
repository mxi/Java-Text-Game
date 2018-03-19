package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

/**
 * This class represents all of the individual tiles
 * in this game.
 *
 * @author Max
 */
public class Tile implements Displayable {

    // Enumeration of types of tiles.
    public enum Type {
        AIR, VOID, WALL
    }

    /**
     * Creates a new air tile.
     * @param parent The parent tile set used to get the font.
     * @return A new air tile.
     */
    public static Tile createAirTile(TileSet parent) {
        return new Tile(new CharacterCell(parent.getFont(), '.'), Type.AIR);
    }

    /**
     * Creates a new void tile.
     * @param parent The parent tile set used to get the font.
     * @return A new void tile.
     */
    public static Tile createVoidTile(TileSet parent) {
        UnicodeFont font = parent.getFont();
        font = Res.modifyFont(font, Color.gray, Res.USE_DEFAULT, Res.USE_DEFAULT, Res.USE_DEFAULT);
        return new Tile(new CharacterCell(font, '~'), Type.VOID);
    }

    public static Tile createWallTile(TileSet parent) {
        Tile tile = new Tile(new CharacterCell(parent.getFont(), 'X'), Type.WALL);
        tile.setTraversable(false);
        return tile;
    }

    private Type tileType; // The type of tile this is.
    private GraphicalElement representation; // The graphical representation of the tile.
    private boolean visible = true; // Whether this tile is visible (will be rendered).
    private boolean traversable = true; // Whether an entity can be located on this tile from movement.

    /**
     * Creates a new Tile object with
     * a sprite for the representation.
     * @param sprite The sprite to represent this tile.
     */
    public Tile(Sprite sprite, Type t) {
        tileType = t;
        representation = sprite;
    }

    /**
     * Creates a new tile object.
     * @param charCell The character cell that represents this tile.
     */
    public Tile(CharacterCell charCell, Type t) {
        tileType = t;
        representation = charCell;
    }

    /**
     * Gets the type of this tile.
     * @return Type of this tile.
     */
    public Type getTileType() {
        return tileType;
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
     * Gets the font of this tile if the representation
     * is a character cell.
     * @return The font used for the character cell representation.
     */
    public UnicodeFont getFont() {
        if(representation instanceof CharacterCell)
            return ((CharacterCell) representation).getFont();
        return null;
    }

    /**
     * Sets the font of the representation if it's
     * a character cell.
     * @param font The new font.
     */
    public void setFont(UnicodeFont font) {
        if(representation instanceof CharacterCell)
            ((CharacterCell) representation).setFont(font);
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
