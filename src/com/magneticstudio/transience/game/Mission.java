package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.LogicalElement;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle a player's mission
 * within the game. This includes managing
 * it's own tileset, entities, and items.
 *
 * @author Max
 */
public class Mission implements LogicalElement {

    private List<Entity> entities; // List of entities in this mission.
    private TileSet tileSet; // The tile set of this mission.

    /**
     * Creates a new mission object.
     */
    public Mission() {
        entities = new ArrayList<>();
        tileSet = new TileSet(40, 40);
        tileSet.getPosition().setTransitionTime(85);
    }

    /**
     * Gets the tile set of this mission.
     * @return The tile set of this mission.
     */
    public TileSet getTileSet() {
        return tileSet;
    }

    /**
     * Sends an update requests to all logical-elements
     * in this mission.
     * @param milliseconds The elapsed time since last update.
     */
    @Override
    public void update(int milliseconds) {
        for(Entity e : entities) {
            e.entityUpdate(tileSet, milliseconds);
        }
        tileSet.update(milliseconds);
    }

    /**
     * Sends a render request to all displayable elements
     * in this mission.
     * @param graphics The graphics used for drawing on main screen.
     */
    public void render(Graphics graphics) throws SlickException {
        tileSet.render(graphics);
    }
}
