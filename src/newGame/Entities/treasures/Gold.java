package newGame.Entities.treasures;

import newGame.Entities.Item;
import sz.csi.ConsoleSystemInterface;

public class Gold extends Item {

    public Gold() {
        super();
        setRepresentation('$');
        setColor(ConsoleSystemInterface.YELLOW);
        setName("Gold");
    }

    @Override
    protected void onItemUse() {

    }
}
