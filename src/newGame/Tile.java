package newGame;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Item;
import newGame.Entities.Representable;
import sz.csi.ConsoleSystemInterface;

public enum Tile {

    WALL(null, 'X', ConsoleSystemInterface.WHITE);

    private InventoryStack<Item> items;
    private char representable;
    private int colour;

    Tile(InventoryStack<Item> inv, char represent, int color) {
        items = inv;
        representable = represent;
        colour = color;
    }

    public InventoryStack<Item> getItems() {
        return items;
    }

    public void setItems(InventoryStack<Item> items) {
        this.items = items;
    }

    public char getRepresentable() {
        return representable;
    }

    public void setRepresentable(char representable) {
        this.representable = representable;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }
}
