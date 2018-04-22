package com.magneticstudio.transience.game;

import com.magneticstudio.transience.util.IntPoint;

import java.util.*;

/**
 * This class is responsible for maintaining
 * a list of all entities in a tile set
 * including the player.
 *
 * @author Max
 */
public class EntityCollection {

    private List<Entity> entities; // The list of entities located in this collection.
    private Player player; // The actual player of this entity collection.

    /**
     * Creates a new entity collection object
     * and sets everything to default.
     */
    public EntityCollection() {
        entities = new ArrayList<>();
    }

    /**
     * Creates the player object.
     */
    public void spawnPlayer(TileSet ts) {
        if(player == null)
            player = new Player(ts);
        IntPoint air = ts.randomPositionOn(Tile.Type.AIR);
        player.setPosition(air.x, air.y);
    }

    /**
     * Gets the player object of this entity collection.
     * @return The player object of this entity collection.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the list of all entities in this array list.
     * @return The list of entities in this collection.
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Goes through all of the entities and performs some
     * action on each individual entity.
     * @param e The ForEach object used to perform an action on an entity.
     */
    public void forEach(ForEach e) {
        for(Entity entity : entities)
            e.onEntity(entity);
        if(player != null)
            e.onEntity(player);
    }

    /**
     * Goes through all of the entities in
     * this collection excluding the player,
     * and performs something on each individual
     * entity.
     * @param e The forEach interface used to process each entity.
     */
    public void forEachNonPlayer(ForEach e) {
        for(Entity entity : entities)
            e.onEntity(entity);
    }

    /**
     * This interface is used to go through each
     * entity in this collection to either render
     * or update them individually.
     */
    public interface ForEach {
        /**
         * Function that runs for an individual entity.
         * @param e The entity this function should process.
         */
        void onEntity(Entity e);
    }
}
