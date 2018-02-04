package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.ArrayList2D;

/**
 * This class manages a 2d array of tiles.
 *
 * @author Max
 */
public class TileSet {

    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d array of tiles.

    public TileSet(int width, int height) {
        setDimensions(width, height);
    }

    public void setDimensions(int width, int height) {
        tiles.setDimensions(
                Math.max(Math.min(width, 1024), 16),
                Math.max(Math.min(height, 1024), 16)
        );
    }
}
