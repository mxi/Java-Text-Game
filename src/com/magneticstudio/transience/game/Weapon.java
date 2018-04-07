package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.GraphicalElement;

/**
 * This class represents a weapon in the game.
 *
 * @author Max
 */
public abstract class Weapon extends Item {

    /**
     * Creates a new item object and uses the
     * specified representation for it.
     *
     * @param representation The representation of this item.
     */
    public Weapon(GraphicalElement representation) {
        super(representation);
    }

    /**
     * Uses this weapon.
     * @param tsLocated The tile set this weapon would be used in.
     * @param wielder The entity wielding this weapon.
     */
    public abstract void attack(TileSet tsLocated, Entity wielder);
}
