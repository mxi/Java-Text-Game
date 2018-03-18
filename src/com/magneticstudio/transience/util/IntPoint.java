package com.magneticstudio.transience.util;

/**
 * A class that represents a 2d point
 * in space.
 *
 * @author Max
 */
public class IntPoint {

    public int x; // The x value of this position.
    public int y; // The y value of this position.

    /**
     * Creates a new point object with
     * default x and y values.
     */
    public IntPoint() {
        x = 0;
        y = 0;
    }

    /**
     * Creates a new point object with
     * specified x and y values.
     * @param nx The x value.
     * @param ny The y value.
     */
    public IntPoint(int nx, int ny) {
        x = nx;
        y = ny;
    }

    /**
     * Checks whether another point object
     * is equivalent to this one.
     * @param p The other point.
     * @return True if they have the same values of x and y.
     */
    public boolean isEquivalentTo(IntPoint p) {
        return p.x == x && p.y == y;
    }

    /**
     * Checks whether another point is
     * equivalent to this one.
     * @param tx The X value of the point.
     * @param ty The Y value of the point.
     * @return Whether this point and the passed point are congruent.
     */
    public boolean isEquivalentTo(int tx, int ty) {
        return x == tx && y == ty;
    }

    /**
     * Gets a string representation of this object.
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
