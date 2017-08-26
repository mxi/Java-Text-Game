package newGame.Mapping;

import newGame.Entities.Entity;
import newGame.IntPoint;
import sz.csi.ConsoleSystemInterface;

import java.util.List;

public interface MapInterface {

    MapBuffer getMapBuffer();
    Tile getTile(int x, int y);
    Tile getTile(IntPoint p);
    void setTile(Tile t, int x, int y);
    void setTile(Tile t, IntPoint p);
    boolean isPassableTile(int x, int y);
    int getEntityCountOf(String name);
    boolean containsEntity(int x, int y);
    Entity getEntity(int x, int y);
    int getMapWidth();
    int getMapHeight();
    int getMinX();
    int getMinY();
    int getMaxX();
    int getMaxY();
    int getFloor();
    void render(ConsoleSystemInterface csi);

    List<Entity> getEntities();
}
