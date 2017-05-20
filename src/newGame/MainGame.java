package newGame;

import newGame.Entities.Character;
import newGame.Entities.CharacterType;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.Monster;
import newGame.Entities.Shield;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGame {

    public static Random random; // Random object
    public static ConsoleSystemInterface csi; // Window (console interface)

    private static List<Monster> monsters; // List of all monsters in the game.
    private static int goblinSpawnChance = 5; // Rarity of a goblin spawning.

    public static void main(String[] args) {
        random = new Random();
    	new MainGame();
        //csi = new WSwingConsoleInterface();
    	//for(;;)
    	//{
        //  random = new Random();
    	//	new Map();
    	//	csi.refresh();
    	//	csi.waitKey(1);
    	//	csi.cls();
    	//}
    }

    private MainGame() {
        random = new Random(); // Creates a new instance of the Random object
        csi = new WSwingConsoleInterface(); // Creates a new instance of WSwingConsoleInterface.
        monsters = new ArrayList<>(); // Creates a new instance of the monsters list.

        /**
         * Begin to initialize characters and other initial
         * parts of the game.
         *
         * Character initialization:
         * TODO: Add character initialization description.
         */
        Character character = new Character("Justin Li", CharacterType.Wizard);
        character.setMaxXY(69, 19);
		character.setPosition(3, 3);
        character.setMaxHealth(20);
        character.setFloor(1);

        new Map();
    	csi.refresh();
        
        // Initializes the main loop to run the game:
        while(true) {

            /**
             * Beginning portion of this loop will display all of the
             * entities/game objects on the window itself.
             */
            // Print Entities:
            csi.print(character.getX(), character.getY(), character.getRepresentation(), character.getColor());
            monsters.forEach(monster ->
                    csi.print(monster.getX(), monster.getY(), monster.getRepresentation(), monster.getColor()));

            // Print Information:
            csi.print(1, 20, "Health: " + character.getHealth() + "/" + character.getMaxHealth() + " ");
            csi.print(1, 21, "Level: " + character.getLevel() + "/" + character.getMaxLevel());
            csi.print(1, 22, "Type: " + character.getTypeAsString());

            /**
             * The next part takes in keyboard input and
             * then processes it using the switch/case statement.
             */
            int key = csi.inkey().code;

            Keys:
            switch(key) {
                case 0:
                    for(Monster m : monsters)
                        if(m.intersects(character.previewMove(0, -1)))
                            break Keys;
                    /*if(csi.peekChar(character.x, character.y - 1) == 'X')
                    {
                    	break;
                    }
                    */
                    character.move(0, -1);
                    break;
                case 1:
                    for(Monster m : monsters)
                        if(m.intersects(character.previewMove(0, 1)))
                            break Keys;
                    /*if(csi.peekChar(character.x, character.y + 1) == 'X')
                    {
                    	break;
                    }
                    */
                    character.move(0, 1);
                    break;
                case 2:
                    for(Monster m : monsters)
                        if(m.intersects(character.previewMove(-1, 0)))
                            break Keys;
                    /*if(csi.peekChar(character.x - 1, character.y) == 'X')
                    {
                    	break;
                    }
                    */
                    character.move(-1, 0);
                    break;
                case 3:
                    for(Monster m : monsters)
                        if(m.intersects(character.previewMove(1, 0)))
                            break Keys;
                    /*if(csi.peekChar(character.x + 1, character.y) == 'X')
                    {
                    	break;
                    }
                    */
                    character.move(1, 0);
                    break;
                case 10:
                	csi.cls();
                    new Map();
                    csi.refresh();
                    break;
                case 64:
                	//newGame.Entities.Weapons.Melee.attack(goblin(0));
                    break;
                default:
            }

            /**
             * Runs the AI of the game. This will move
             * monsters, spawn monsters, update the character,
             * and spawn more items.
             */
            runAI(character);

            //csi.cls();
            csi.refresh();
        }
    }

    private void runAI(Character c) {
        monsters.forEach(m -> m.performAI(c));

        // #region spawn monsters
        if(random.nextInt(101) <= goblinSpawnChance) {
            Goblin goblin = new Goblin();
            goblin.setLevel(1);
            goblin.setMinXY(0, 0);
            goblin.setMaxXY(69, 19);
            monsters.add(goblin);
        }
    }
}
