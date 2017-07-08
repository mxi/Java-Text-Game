package newGame.Mapping;

import java.util.ArrayList;
import java.util.List;

public class MapBuffer {

    private List<List<Tile>> tiles;
    private int width;
    private int height;

    public MapBuffer() {
        tiles = new ArrayList<>();
        width = 0;
        height = 0;
    }

    public MapBuffer(int width, int height) {
        tiles = new ArrayList<>();
        setHeight(height);
        setWidth(width);
    }

    public void setTile(Tile t, int x, int y) {
        if(y >= tiles.size()) {
            setHeight(y);
        }
        if(x >= tiles.get(y).size()) {
            setWidth(x);
        }
        tiles.get(y).set(x, t);
    }

    public Tile getTile(int x, int y) {
        if(x < width && y < height) {
            return tiles.get(y).get(x);
        }
        else {
            return Tile.EMPTY;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        fitToWidth(width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        fitToHeight(height);
    }

    private void fitTo(int x, int y) {
        fitToHeight(y);
        fitToWidth(x);
    }

    private void fitToHeight(int y) {
        final int oldtsize = tiles.size();
        for(int i = 0; i < (y - oldtsize) + 1; i++) {
            final List<Tile> tileRow = new ArrayList<>();
            for(int j = 0; j < width; j++) {
                tileRow.add(Tile.EMPTY);
            }
            tiles.add(tileRow);
        }
    }

    private void fitToWidth(int x) {
        for(List<Tile> tileRow : tiles) {
            final int oldrsize = tileRow.size();
            for(int i = 0; i < (x - oldrsize) + 1; i++) {
                tileRow.add(Tile.EMPTY);
            }
        }
    }
}
