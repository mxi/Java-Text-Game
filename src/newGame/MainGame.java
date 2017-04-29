package newGame;

import newGame.Entities.Character;
import newGame.Entities.CharacterType;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.Monster;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.ArrayList;
import java.util.Random;

public class MainGame {

    public static Random random;
    public static ConsoleSystemInterface csi;

    private static MonsterList monsters;
    private static int goblinSpawnChance = 15;

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
        monsters = new MonsterList();
        monsters.setBounds(1, 1, 69, 15);

        // Character & Game initialization:
        Character character = new Character("John", CharacterType.Fighter, ConsoleSystemInterface.CYAN, 1);
        character.setMaxXY(69, 15);
        character.setPosition(1, 1);
        character.setMaxHealth(20);

        // Main game loop:
        while(true) {
            csi.print(character.getX(), character.getY(), character.getRepresentation(), character.getColor());
            monsters.getMonsters().forEach(monster ->
                    csi.print(monster.getX(), monster.getY(), monster.getRepresentation(), monster.getColor()));
            // 0 = Down
            // 1 = Up
            // 2 = Left
            // 3 = Right
            int key = csi.inkey().code;

            switch(key) {
                case 0:
                    for(Monster m : monsters.getMonsters())
                        if(m.intersects(character.previewDown()))
                            break;
                    break;
                case 1:
                    character.moveUp();
                    break;
                case 2:
                    character.moveLeft();
                    break;
                case 3:
                    character.moveRight();
                    break;
            }

            character.onKeyPress(key);
            monsters.getMonsters().forEach(monster -> monster.onKeyPress(key));

            runAI();

            csi.cls();
            csi.refresh();
        }
    }

    private void runAI() {
        if(random.nextInt(101) <= goblinSpawnChance) {
            monsters.spawnGoblin(1);
        }
    }
}
