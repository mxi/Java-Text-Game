package com.magneticstudio.transience.game;

/**
 * This class represents an entity that is not
 * controlled by the player.
 *
 * @author Max
 */
public abstract class ComputerEntity extends Entity {

    /**
     * Creates a new ComputerEntity object
     * with default properties.
     */
    public ComputerEntity(TileSet onTileSet) {
        super(onTileSet);
    }

    @Override
    public void entityUpdate(TileSet tsLocated, int milliseconds) {

    }
}
