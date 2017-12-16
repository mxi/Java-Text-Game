package newGame.Mapping;

import newGame.Entities.Entity;
import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Item;
import newGame.Entities.Representable;
import sz.csi.ConsoleSystemInterface;

import java.io.Console;

public class Tile {

    public static Tile empty() {
        return new Tile("Void", null, '~', true, ConsoleSystemInterface.GRAY);
    }

    public static Tile space() {
        return new Tile("Space", null, '.', false, ConsoleSystemInterface.WHITE);
    }

    public static Tile stair() {
        return new Tile("Stair", null, '\\', false, ConsoleSystemInterface.WHITE);
    }

    public static Tile healTile() {
        return new Tile("healTile", null, 'O', false, ConsoleSystemInterface.CYAN);
    }
    
    public static Tile wall() {
        return new Tile("Wall", null, 'X', true, ConsoleSystemInterface.WHITE);
    }

    public static final Tile EMPTY = empty();
    public static final Tile SPACE = space();
    public static final Tile STAIR = stair();
    public static final Tile WALL = wall();
    public static final Tile HEALTILE = healTile();

    private String name;
    private InventoryStack<Item> items;
    private char representable;
    private boolean solid;
    private int colour;

    public Tile(String n, InventoryStack<Item> inv, char represent, boolean isSolid, int color) {
        if(!isSolid) {
            items = inv;
        }
        name = n;
        representable = represent;
        solid = isSolid;
        colour = color;
    }

    public boolean hasItems() {
        return items != null && items.getSize() > 0;
    }

    public boolean equalsTo(Tile t) {
        return similar(t) && t.getInventoryStack() == getInventoryStack();
    }

    public boolean similar(Tile t) {
        return t.getColor() == colour && t.getRepresentation() == representable;
    }

    public InventoryStack<Item> getInventoryStack() {
        return items;
    }

    public void setInventoryStack(InventoryStack<Item> items) {
        if(!isSolid())
            this.items = items;
    }

    public char getRepresentation() {
        return representable;
    }

    public void setRepresentation(char representable) {
        this.representable = representable;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public int getColor() {
        return colour;
    }

    public void setColor(int colour) {
        this.colour = colour;
    }

    public Representable getRepresentable() {
        return new Representable(representable, colour);
    }

    @Override
    public String toString() {
        return name;
    }
}
