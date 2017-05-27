package newGame;

import java.awt.*;
import java.util.List;

public interface MapInterface {

    char getCharacter(int x, int y);
    char getCharacter(Point p);
    int getMapWidth();
    int getMapHeight();

    List<Map.Hallway> getHallways();
    List<Map.Room> getRooms();
}
