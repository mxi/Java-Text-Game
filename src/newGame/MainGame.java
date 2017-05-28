package newGame;

import newGame.Entities.Character;
import newGame.Entities.CharacterType;
import newGame.Entities.Entity;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.Monster;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.Random;

public class MainGame {

    public static Random random; // Random object
    public static ConsoleSystemInterface csi; // Window (console interface)
    public static MapInterface map; // Map of the game.

    private static Character character; // Character of the game.

    private static int goblinSpawnChance = 5; // Rarity of a goblin spawning.

    public static void main(String[] args) {
    	new MainGame();
    }

    private MainGame() {
        random = new Random(); // Creates a new instance of the Random object
        csi = new WSwingConsoleInterface(); // Creates a new instance of WSwingConsoleInterface.
        map = new Map();
        csi.refresh();

        /**
         * Begin to initialize characters and other initial
         * parts of the game.
         *
         * Character initialization:
         * TODO: Add character initialization description.
         */
    	
        character = new Character("Justin Li", CharacterType.Wizard);
        character.setMaxXY(69, 19);
        character.setMaxHealth(20);
        character.setFloor(1);
        character.spawn('.');

        Entity.entities.add(character);
        csi.refresh();

        // Initializes the main loop to run the game:
        while(true) {

            /**
             * Beginning portion of this loop will display all of the
             * entities/game objects on the window itself.
             */
            // Print Entities:
            Entity.entities.forEach(e ->
                    csi.print(e.getX(), e.getY(), e.getRepresentation(), e.getColor()));

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
                    character.move(0, -1);
                    break;
                case 1:
                    character.move(0, 1);
                    break;
                case 2:
                    character.move(-1, 0);
                    break;
                case 3:
                    character.move(1, 0);
                    break;
                case 10:
                    // Creates a new map interface/object when the
                    // player presses the space bar on a stair character.
                    if(character.getPrevCharOfMap() == '/') {
                        csi.cls();
                        Entity.entities.removeIf(e -> !(e instanceof Character));
                        map = new Map();
                        csi.refresh();
                        character.spawn('/');
                    }
                    break;
                case 64:
                    break;
                default:
                	//System.out.println(key);
                    break;
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
        Entity.entities.forEach(entity -> {
            if(entity instanceof Monster)
            	if(((Monster) entity).FindMode)
            		((Monster) entity).findAI(character, random.nextInt(5), 10/*((Monster) entity).Persistance*/);
            	else
            		((Monster) entity).performAI(character);
        });

        // #region spawn monsters
        if(random.nextInt(101) <= goblinSpawnChance) {
            Goblin goblin = new Goblin();
            goblin.setRepresentation('G');
            goblin.setColor(ConsoleSystemInterface.GREEN);
            goblin.setLevel(1);
            goblin.setMinXY(1, 1);
            goblin.setMaxXY(69, 19);
            goblin.spawn('.');
            Entity.entities.add(goblin);
        }
    }
}
