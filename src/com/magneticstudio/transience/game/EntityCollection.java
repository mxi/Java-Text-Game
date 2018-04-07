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

    private TileSet containingTileSet; // The containing tile set of this entity collection.
    private Map<Faction, List<Entity>> coreCollection = new HashMap<>(); // The underlying structure of the collection of entities.
    private Faction playerFaction; // The faction the player is a part of.
    private Player player; // The actual player of this entity collection.

    /**
     * Creates a new entity collection object
     * and sets everything to default.
     */
    public EntityCollection(TileSet container) {
        for(Faction f : Faction.values()) {
            this.coreCollection.put(f, new ArrayList<>());
        }
        this.containingTileSet = container;
        this.playerFaction = Faction.NONE;
    }

    /**
     * Creates the player object.
     */
    public void spawnPlayer() {
        if(player == null)
            player = new Player(containingTileSet);
        IntPoint air = containingTileSet.randomPositionOn(Tile.Type.AIR);
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
     * Gets the faction the player is a part of.
     * @return The faction that the player is a part of.
     */
    public Faction getPlayerFaction() {
        return playerFaction;
    }

    /**
     * Returns the list of entities present in this collection
     * from the specified faction. The factions don't include the
     * player.
     * @param f The faction to list the entities of.
     * @return The list of entities from that faction in this collection.
     */
    public List<Entity> getEntities(Faction f) {
        return coreCollection.get(f);
    }

    /**
     * Goes through all of the entities in
     * this collection and performs something
     * on each individual entity.
     * @param e The forEach interface used to process each entity.
     */
    public void forEach(ForEach e) {
        for(Map.Entry<Faction, List<Entity>> entry : coreCollection.entrySet()) {
            Faction faction = entry.getKey();
            for(Entity ind : entry.getValue())
                e.onEntity(faction, ind);
        }
        if(player != null)
            e.onEntity(playerFaction, player);
    }

    /**
     * This interface is used to go through each
     * entity in this collection to either render
     * or update them individually.
     */
    public interface ForEach {
        /**
         * Function that runs for an individual entity.
         * @param f The faction that this entity is a part of.
         * @param e The entity this function should process.
         */
        void onEntity(Faction f, Entity e);
    }
}
