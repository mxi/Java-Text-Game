package newGame;

import newGame.Entities.Entity;
import newGame.Mapping.MapBuffer;
import sz.csi.ConsoleSystemInterface;

import java.util.List;

public interface MapInterface {

    MapBuffer getMapBuffer();
    Tile getTile(int x, int y);
    Tile getTile(IntPoint p);
    void setTile(Tile t, int x, int y);
    void setTile(Tile t, IntPoint p);
    int getEntityCountOf(String name);
    int getMapWidth();
    int getMapHeight();
    int getMinX();
    int getMinY();
    int getMaxX();
    int getMaxY();
    void display(ConsoleSystemInterface csi);

    List<Entity> getEntities();
}
