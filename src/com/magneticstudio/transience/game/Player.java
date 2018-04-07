package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.CharacterCell;
import com.magneticstudio.transience.ui.GameKeyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * This class is the actual player class that the user
 * will be able to control.
 *
 * @author Max
 */
public class Player extends Entity {

    private static final int KEY_GO_UP = Input.KEY_W;
    private static final int KEY_GO_LEFT = Input.KEY_A;
    private static final int KEY_GO_RIGHT = Input.KEY_D;
    private static final int KEY_GO_DOWN = Input.KEY_S;

    // inv slot 1
    // inv slot 2
    // inv slot 3

    /**
     * Creates a new player object.
     * @param onTileSet The tile set the entity will be on.
     */
    public Player(TileSet onTileSet) {
        super(onTileSet);
        CharacterCell representation = (CharacterCell) getRepresentation();
        representation.setColor(new Color(255, 255, 50, 255));
        representation.setCharacter('@');
        getPosition().setTransitionTime(GameKeyboard.KEY_COOLDOWN_TIME);
    }

    /**
     * Updates this player object.
     * @param tsLocated The tile set this entity is located on.
     * @param milliseconds The time in milliseconds since the last update.
     */
    public void update(TileSet tsLocated, int milliseconds) {
        int newX = getX();
        int newY = getY();
        if(GameKeyboard.isTapped(KEY_GO_UP))
            newY--;
        else if(GameKeyboard.isTapped(KEY_GO_LEFT))
            newX--;
        else if(GameKeyboard.isTapped(KEY_GO_RIGHT))
            newX++;
        else if(GameKeyboard.isTapped(KEY_GO_DOWN))
            newY++;
        else if(GameKeyboard.isTapped(Input.KEY_Z))
            tsLocated.setScale(.25f, 500);
        else if(GameKeyboard.isTapped(Input.KEY_U))
            tsLocated.setScale(1f, 500);
        else if(GameKeyboard.isTapped(Input.KEY_R))
            tsLocated.setScale(.5f, 500);
        else if(GameKeyboard.isTapped(Input.KEY_L))
            tsLocated.shake(12f, 3f, 1000);

        if(newX != getX() || newY != getY()) {
            Tile toMoveTo = tsLocated.getTiles().getElement(newX, newY);
            if(toMoveTo != null && toMoveTo.isTraversable()) {
                setPosition(newX, newY);
                tsLocated.runAi();
            }
        }
    }

    /**
     * Renders this player (and other things) onto the screen.
     * @param graphics The graphics object used to render anything on the main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        getRepresentation().render(graphics, x, y, centerSurround);

    }
}
