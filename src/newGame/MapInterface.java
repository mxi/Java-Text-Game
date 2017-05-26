package newGame;

import java.util.List;

public interface MapInterface {

	char getCharacter(int x, int y);
    int getMapWidth();
    int getMapHeight();

    List<Map.Hallway> getHallways();
    List<Map.Room> getRooms();
}
