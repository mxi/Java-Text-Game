package newGame.Animations;

import newGame.Entities.Entity;
import newGame.IntPoint;

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
     * Gets the direction of a swing as from
     * two locations defined as IntPoint objects.
     * @param pos1 First position (attacker)
     * @param pos2 Second position (defender)
     * @return The direction of the swing.
     */
    public static int getFromPositionOffset(IntPoint pos1, IntPoint pos2) {
        if(pos1.isEquivalentTo(pos2))
            return -1; // Can't have equal positions (don't know which direction to swing at)

        int dx = pos2.getX() - pos1.getX();
        int dy = pos2.getY() - pos1.getY();
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

    /**
     * Gets the direction based on the difference
     * of X and Y values of the two entity positions.
     * @param e1 The first entity (the attacker)
     * @param e2 The second entity (the defender)
     * @return The number representing swing direction of entity 1.
     */
    public static int getFromPositionOffset(Entity e1, Entity e2) {
        return getFromPositionOffset(e1.getPosition(), e2.getPosition());
    }
}
