package newGame;

import newGame.Entities.Character;
import newGame.Entities.EntityAttributes;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.Monster;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.List;
import java.util.Random;

public class MainGame {

    public static Random random;
    public static ConsoleSystemInterface csi;

    private static List<Monster> monsters;
    private static int goblinSpawnChance = 40;

    public static void main(String[] args) {
    	//new MainGame();
        csi = new WSwingConsoleInterface();
    	for(;;)
    	{
          random = new Random();
    		new Map();
    		csi.refresh();
    		csi.waitKey(1);
    		csi.cls();
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
            csi.print(character.getX(), character.getY(), character.getRepresentation(), character.getColor());
            monsters.forEach(monster ->
                    csi.print(monster.getX(), monster.getY(), monster.getRepresentation(), monster.getColor()));
            // 0 = Down
            // 1 = Up
            // 2 = Left
            // 3 = Right
            int key = csi.inkey().code;

            switch(key) {
                case 0: // Down
                    character.moveDown();
                    runAI();
                    break;
                case 1: // Up
                    character.moveUp();
                    runAI();
                    break;
                case 2: // Left
                    character.moveLeft();
                    runAI();
                    break;
                case 3: // Right
                    character.moveRight();
                    runAI();
                    break;
                default: // Other keys can be processed here:
                    break;
            }

            csi.cls();
            csi.refresh();
        }
    }

    private void runAI() {
        // Spawn goblin:
        if(random.nextInt(100) < goblinSpawnChance) {
            Goblin newGoblin = new Goblin(1);
            newGoblin.setPosition(random.nextInt(70), random.nextInt(16));
            newGoblin.setMaxXY(69, 15);
            monsters.add(newGoblin);
        }
    }
}
