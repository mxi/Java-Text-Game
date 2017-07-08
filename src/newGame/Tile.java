package newGame;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Item;
import sz.csi.ConsoleSystemInterface;

public enum Tile {

    EMPTY(null, ' ', true, ConsoleSystemInterface.BLACK),
    WALL(null, 'X', true, ConsoleSystemInterface.WHITE),
	SPACE(null, '.', false, ConsoleSystemInterface.WHITE),
	STAIR(null, '/', false, ConsoleSystemInterface.WHITE);

    private InventoryStack<Item> items;
    private char representable;
    private boolean solid;
    private int colour;

    Tile(InventoryStack<Item> inv, char represent, boolean isSolid, int color) {
        if(!isSolid) {
            items = inv;
        }
        representable = represent;
        solid = isSolid;
        colour = color;
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
}
