package newGame;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Representable;
import sz.csi.ConsoleSystemInterface;

public class Tile extends Representable {

    private InventoryStack<?> invStack;

    public Tile() {
        super('.', ConsoleSystemInterface.WHITE);
    }

    public Tile(char represent, int color) {
        super(represent, color);
    }

    public Tile(InventoryStack<?> inv, char represent, int color) {
        super(represent, color);
        setInventoryStack(inv);
    }

    public InventoryStack<?> getInventoryStack() {
        return invStack;
    }

    public void setInventoryStack(InventoryStack<?> invStack) {
        this.invStack = invStack;
    }
}
