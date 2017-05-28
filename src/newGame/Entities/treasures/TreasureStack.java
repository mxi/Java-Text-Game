package newGame.Entities.treasures;

import newGame.Entities.InventoryStack;
import sz.csi.ConsoleSystemInterface;

public class TreasureStack extends InventoryStack {

    public TreasureStack() {
        super(5);
        setName("TreasureStack<Treasure>");
        setRepresentation('T');
        setColor(ConsoleSystemInterface.TEAL);
    }
}
