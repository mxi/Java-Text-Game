package newGame;

import newGame.Entities.Character;
import newGame.Entities.EntityAttributes;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.Random;

public class MainGame {

    public static Random random;
    public static ConsoleSystemInterface csi;

    public static void main(String[] args) {
    	for(;;)
    	{
    		new MainGame();
    	}
    }

    private MainGame() {
        random = new Random();
        csi = new WSwingConsoleInterface();

        // Character & Game initialization:
        Character character = new Character("John", EntityAttributes.CharacterType.Fighter, ConsoleSystemInterface.CYAN, 1);
        character.setMaxXY(69, 15);
        character.setPosition(1, 1);
        character.setMaxHealth(20);

        // Main game loop:
        while(true) {
            csi.print(character.getX(), character.getY(), "@", character.getColor());
            // 0 = Down
            // 1 = Up
            // 2 = Left
            // 3 = Right
            int key = csi.inkey().code;

            switch(key) {
                case 0: // Down
                    character.moveDown();
                    break;
                case 1: // Up
                    character.moveUp();
                    break;
                case 2: // Left
                    character.moveLeft();
                    break;
                case 3: // Right
                    character.moveRight();
                    break;
                default: // Other keys can be processed here:
                    break;
            }

            csi.cls();
            csi.refresh();
        }
    }
}
