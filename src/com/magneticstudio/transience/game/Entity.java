package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.*;
import com.magneticstudio.transience.util.FlowPosition;
import com.magneticstudio.transience.util.IntPoint;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * This class would represent an entity in the
 * game such as the character or an enemy.
 *
 * @author Max
 */
public abstract class Entity implements Displayable {

    private GraphicalElement representation; // The representation of this entity.
    private FlowPosition position = new FlowPosition(0, 0); // The position of this entity.

    /**
     * Creates a new Entity object.
     * @param onTileSet The tile set the entity will be on.
     */
    public Entity(TileSet onTileSet) {
        representation = new CharacterCell(
            Res.modifyFont(
                    onTileSet.getFont(),
                Color.red,
                Res.USE_DEFAULT,
                Res.USE_DEFAULT,
                Res.USE_DEFAULT
            ), 'O'
        );
        position.setTransitionTime(GameKeyboard.KEY_COOLDOWN_TIME);
    }

    /**
     * Gets the representation of this entity.
     * @return Representation of this entity.
     */
    public GraphicalElement getRepresentation() {
        return representation;
    }

    /**
     * Gets the X value of the position.
     * @return X value of the position.
     */
    public int getX() {
        return position.getTargetX();
    }

    /**
     * Sets the X value of the position.
     * @param x X value of the position.
     */
    public void setX(int x) {
        position.setTargetX(x);
    }

    /**
     * Gets the Y value of the position.
     * @return Y value of the position.
     */
    public int getY() {
        return position.getTargetY();
    }

    /**
     * Sets the Y value of the position.
     * @param y Y value of the position.
     */
    public void setY(int y) {
        position.setTargetY(y);
    }

    /**
     * Sets the position of this entity.
     * @param x X value of the position.
     * @param y Y value of the position.
     */
    public void setPosition(int x, int y) {
        position.setTargetPosition(x, y);
    }

    /**
     * Moves this entity in the horizontal.
     * @param x Amount to move in the horizontal.
     */
    public void moveX(int x) {
        setX(position.getTargetX() + x);
    }

    /**
     * Moves this entity in the vertical.
     * @param y Amount to move in the vertical.
     */
    public void moveY(int y) {
        setY(position.getTargetY() + y);
    }

    /**
     * Moves this entity in the specified direction.
     * @param x X value of the vector.
     * @param y Y value of the vector.
     */
    public void move(int x, int y) {
        setX(position.getTargetX() + x);
        setY(position.getTargetY() + y);
    }

    /**
     * Moves this entity in the specified direction.
     * @param vec Movement vector.
     */
    public void move(IntPoint vec) {
        setX(position.getTargetX() + vec.x);
        setY(position.getTargetY() + vec.y);
    }

    /**
     * Gets the position of this entity.
     * @return Position of this entity.
     */
    public FlowPosition getPosition() {
        return position;
    }

    /**
     * Updates this entity's position.
     * @param milliseconds The elapsed time in milliseconds.
     */
    public void updatePosition(int milliseconds) {
        position.update(milliseconds);
    }

    /**
     * Updates the entity object.
     * @param tsLocated The tile set this entity is located on.
     * @param milliseconds The time in milliseconds since the last update.
     */
    public abstract void entityUpdate(TileSet tsLocated, int milliseconds);

    /**
     * Renders this entity onto the screen.
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
