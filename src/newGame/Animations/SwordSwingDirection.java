package newGame.Animations;

import newGame.Entities.Entity;

/**
 * This enumeration signals what direction the
 * sword is being swung on attack.
 */
public class SwordSwingDirection {

    /**
     * The number mapping of each
     * 8 directions.
     */
    public static final int NORTHWEST = 0;
    public static final int NORTH = 1;
    public static final int NORTHEAST = 2;
    public static final int WEST = 3;
    public static final int EAST = 4;
    public static final int SOUTHWEST = 5;
    public static final int SOUTH = 6;
    public static final int SOUTHEAST = 7;

    /**
     * Gets the direction based on the difference
     * of X and Y values of the two entity positions.
     * @param e1 The first entity (the attacker)
     * @param e2 The second entity (the defender)
     * @return The number representing swing direction of entity 1.
     */
    public static int getFromPositionOffset(Entity e1, Entity e2) {
        if(e1.getPosition().isEquivalentTo(e2.getPosition()))
            return -1; // Can't have equal positions (don't know which direction to swing at)

        int dx = e2.getX() - e1.getX();
        int dy = e2.getY() - e1.getY();
        if(Math.abs(dx) > 1 || Math.abs(dy) > 1)
            return -1; // Direction may not be one of the 8 directions.

        if(dx == -1 && dy == -1)
            return NORTHWEST;
        else if(dx == 0 && dy == -1)
            return NORTH;
        else if(dx == 1 && dy == -1)
            return NORTHEAST;
        else if(dx == -1 && dy == 0)
            return WEST;
        else if(dx == 1 && dy == 0)
            return EAST;
        else if(dx == -1 && dy == 1)
            return SOUTHWEST;
        else if(dx == 0 && dy == 1)
            return SOUTH;
        else if(dx == 1 && dy == 1)
            return SOUTHEAST;
        else
            return -1; // Can't happen, but should be here just in case.
    }
}
