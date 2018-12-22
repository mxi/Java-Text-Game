package com.magneticstudio.transience.game;

/**
 * A computer controlled entity that will
 * attack the player.
 *
 * @author Max
 */
public abstract class Enemy extends Entity {

    /**
     * Creates a new Entity object.
     * @param onTileSet The tile set the entity will be on.
     */
    public Enemy(TileSet onTileSet) {
        super(onTileSet);
    }

    /**
     * Runs this enemy's AI.
     * @param ts The tile set this
     */
    public abstract void runAi(TileSet ts);
}
