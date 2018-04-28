package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.CharacterCell;
import com.magneticstudio.transience.ui.Game;
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
    private static final int KEY_INTERACT_WITH_TILE = Input.KEY_ENTER;

    // inv slot 1
    // inv slot 2
    // inv slot 3

    /**
     * Creates a new player object.
     * @param onTileSet The tile set the entity will be on.
     */
    public Player(TileSet onTileSet) {
        super(onTileSet);
        if(getRepresentation() instanceof CharacterCell) {
            CharacterCell representation = (CharacterCell) getRepresentation();
            representation.setColor(new Color(255, 255, 50, 255));
            representation.setCharacter('@');
        }
        getPosition().setTransitionTime(GameKeyboard.KEY_COOLDOWN_TIME);
    }

    /**
     * Updates this player object.
     * @param tsLocated The tile set this entity is located on.
     */
    public void update(TileSet tsLocated) {
        int newX = getX();
        int newY = getY();
        switch(GameKeyboard.getTappedKey()) {
            case KEY_GO_UP:              newY--; break;
            case KEY_GO_DOWN:            newY++; break;
            case KEY_GO_LEFT:            newX--; break;
            case KEY_GO_RIGHT:           newX++; break;
            case Input.KEY_F:            Game.activeGame.fadeOut(250); break;
            case Input.KEY_G:            Game.activeGame.fadeIn(250); break;
            case KEY_INTERACT_WITH_TILE: interactWithTile(tsLocated, getX(), getY()); break;
        }

        if(newX != getX() || newY != getY()) {
            Tile toMoveTo = tsLocated.getTiles().getElement(newX, newY);
            if(toMoveTo != null && toMoveTo.isTraversable()) {
                setPosition(newX, newY);
                tsLocated.runAi();
            }
        }
    }

    /**
     * Interacts with a given tile.
     * @param ts The tile set the tile is located on.
     * @param tx The row the tile is located on.
     * @param ty The column the tile is located on.
     */
    private void interactWithTile(TileSet ts, int tx, int ty) {
        if(ts.getTiles().getElement(tx, ty).getTileType() == Tile.Type.STAIR) {
            Game.activeGame.disableInputUpdatesWhileFading(true);
            Game.activeGame.setOnFadedOut(() -> {
                ts.getGenerator().regenerate(ts);
                Game.activeGame.fadeIn(500);
            });
            Game.activeGame.fadeOut(1000);
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
