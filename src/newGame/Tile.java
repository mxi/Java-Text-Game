package newGame;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Item;
import newGame.Entities.Representable;
import sz.csi.ConsoleSystemInterface;

public enum Tile {

    WALL(null, 'X', ConsoleSystemInterface.WHITE),
	SPACE(null, '.', ConsoleSystemInterface.WHITE),
	STAIR(null, '/', ConsoleSystemInterface.WHITE);

    private InventoryStack<Item> items;
    private char representable;
    private int colour;

    Tile(InventoryStack<Item> inv, char represent, int color) {
        items = inv;
        representable = represent;
        colour = color;
    }

    public boolean equalsTo(Tile t) {
        return similar(t) && t.getInventoryStack().equals(items);
    }

    public boolean similar(Tile t) {
        return t.getColor() == colour && t.getRepresentation() == representable;
    }

    public InventoryStack<Item> getInventoryStack() {
        return items;
    }

    public void setInventoryStack(InventoryStack<Item> items) {
        this.items = items;
    }

    public char getRepresentation() {
        return representable;
    }

    public void setRepresentation(char representable) {
        this.representable = representable;
    }

    public int getColor() {
        return colour;
    }

    public void setColor(int colour) {
        this.colour = colour;
    }
}
