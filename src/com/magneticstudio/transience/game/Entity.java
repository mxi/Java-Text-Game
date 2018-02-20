package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.CharacterCell;
import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.GraphicalElement;
import com.magneticstudio.transience.util.Cache;
import com.magneticstudio.transience.util.FlowPosition;
import com.magneticstudio.transience.util.IntPoint;
import org.newdawn.slick.Graphics;

/**
 * This class would represent an entity in the
 * game such as the character or an enemy.
 *
 * @author Max
 */
public abstract class Entity implements Displayable {

    private GraphicalElement representation = new CharacterCell(Cache.DEFAULT_FONT, '&'); // The representation of this entity.
    private FlowPosition position = new FlowPosition(0, 0); // The position of this entity.
    private IntPoint previousPosition = position.getIntPoint(); // The previous location of this entity.

    /**
     * Creates a new Entity object.
     */
    public Entity() {

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
        previousPosition.setX(position.getTargetX());
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
        previousPosition.setY(position.getTargetY());
        position.setTargetY(y);
    }

    /**
     * Sets the position of this entity.
     * @param x X value of the position.
     * @param y Y value of the position.
     */
    public void setPosition(int x, int y) {
        previousPosition.setX(position.getTargetX());
        previousPosition.setY(position.getTargetY());
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
        setX(position.getTargetX() + vec.getX());
        setY(position.getTargetY() + vec.getY());
    }

    /**
     * Gets the position of this entity.
     * @return Position of this entity.
     */
    public IntPoint getPosition() {
        return position.getIntPoint();
    }

    /**
     * Gets the previous position of this entity.
     * @return Previous position of this entity.
     */
    public IntPoint getPreviousPosition() {
        return previousPosition;
    }

    /**
     * Updates the entity object.
     * @param milliseconds The time in milliseconds since the last update.
     */
    public abstract void entityUpdate(int milliseconds);

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
