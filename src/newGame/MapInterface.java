package newGame;

import newGame.Entities.Entity;

import java.util.List;

public interface MapInterface {

    int getEntityCountOf(String name);
    char getCharacter(int x, int y);
    char getCharacter(IntPoint p);
    void setCharacter(char c, int x, int y, int color);
    int getMapWidth();
    int getMapHeight();
    int getMinX();
    int getMinY();
    int getMaxX();
    int getMaxY();

    List<Entity> getEntities();
}
