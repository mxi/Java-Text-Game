package newGame;

import java.awt.*;
import java.util.List;

public interface MapInterface {

    char getCharacter(int x, int y);
    char getCharacter(IntPoint p);
    void setCharacter(char c, int x, int y, int color);
    int getMapWidth();
    int getMapHeight();
    int getMinX();
    int getMinY();
    int getMaxX();
    int getMaxY();

    List<Map.Hallway> getHallways();
    List<Map.Room> getRooms();
}
