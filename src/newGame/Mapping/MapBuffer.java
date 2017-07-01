package newGame.Mapping;

import newGame.Tile;

public class MapBuffer {

    private Tile[][] tiles;
    private int width;
    private int height;

    public MapBuffer() {

    }

    public MapBuffer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setTile(Tile t, int x, int y) {
        if(x < width && x >= 0 && y < height && y >= 0) {
            tiles[x][y] = t;
        }
    }

    public Tile getTile(int x, int y) {
        try {
            return tiles[x][y];
        }
        catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
