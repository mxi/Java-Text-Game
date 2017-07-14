package newGame.Mapping;

import newGame.IntPoint;
import newGame.MainGame;

/**
 * Provides a way to store multiple types of data
 * in one tile of the map.
 *
 * (Previously used the 'char' type; it didn't allow
 * storage of InventoryStacks)
 */
public class MapBuffer extends ArrayList2D<Tile> {

    /**
     * Default MapBuffer constructor; uses
     * default settings for the 2d ArrayList inside the
     * ArrayList2D default constructor.
     */
    public MapBuffer() {
        super();
    }

    /**
     * Constructor that will initialize the super class with
     * the width and height parameters.
     * @param width Width of the map buffer.
     * @param height Height of the map buffer.
     */
    public MapBuffer(int width, int height) {
        super(width, height);
    }

    /**
     * Randomly picks a location of a sepcified type.
     * @param oftype Tile type to find a location on.
     * @return Position of a located tile equivalent to the one specified.
     */
    public IntPoint randomLoc(Tile oftype) {
        final int maxAttempts = 1024;
        int attempts = 0;
        int rx;
        int ry;
        Tile t;
        do {
            rx = MainGame.random.nextInt(getWidth());
            ry = MainGame.random.nextInt(getHeight());
            t = getElement(rx, ry);
            attempts++;
        }
        while(!t.equalsTo(oftype) && attempts <= maxAttempts);
        return new IntPoint(rx, ry);
    }
}
