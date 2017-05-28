package newGame;

import java.awt.*;

public class MapScanner {

    private MapInterface map;

    private IntPoint currentLoc;
    private char scanFor;

    public MapScanner(MapInterface mapToScan, char forScan) {
        map = mapToScan;
        scanFor = forScan;
    }

    public IntPoint getNext() {
        for(int x = currentLoc.getX(); x <= map.getMaxX(); x++) {
            for (int y = (int) currentLoc.getY(); y <= map.getMaxY(); y++) {
                if(map.getCharacter(x, y) == scanFor) {
                    currentLoc = new IntPoint(x, y);
                    return currentLoc;
                }
            }
        }

        return null;
    }
}
