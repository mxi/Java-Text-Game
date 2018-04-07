package newGame.Entities.treasures;

import newGame.Entities.Entity;
import newGame.Entities.Item;
import newGame.Entities.Character;
import newGame.MainGame;
import newGame.menus.Menu;
import sz.csi.ConsoleSystemInterface;

public class Gold extends Item {

    public Gold() {
        super();
        setRepresentation('$');
        setColor(ConsoleSystemInterface.YELLOW);
        setName("Gold");
        setIsOneTimeUse(false);
    }

    @Override
    protected void onItemUse() {
        Entity owner = getOwner();
        if(owner instanceof Character) {
            Character character = (Character) owner;
            if(character.distance(MainGame.map.getTraderPost()) <= Math.sqrt(2)) {
                StringBuilder titleAndBalance = new StringBuilder();
                titleAndBalance.append("Trader Joe's");
                String balance = Integer.toString(character.getGoldAmount());

                final int space = 22 - balance.length();
                for(int i = 0; i < space; i++)
                    titleAndBalance.append(' ');
                titleAndBalance.append("Balance: ").append(balance).append(" Gold");

                Menu menu = new Menu(50, 18, false);
                menu.setTitle(titleAndBalance.toString());
                menu.setShown(true);
            }
        }
    }
}
