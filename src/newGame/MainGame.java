package newGame;

import newGame.Entities.Character;
import newGame.Entities.CharacterType;
import newGame.Entities.Entity;
import newGame.Entities.Item;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.Monster;
import newGame.Entities.Weapons.LongSword;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.Random;

public class MainGame {

    public static Random random; // Random object
    public static ConsoleSystemInterface csi; // Window (console interface)
    public static MapInterface map; // Map of the game.

    private static Character character; // Character of the game.

    private static int goblinSpawnChance = 2; // Rarity of a goblin spawning.

    public static void main(String[] args) {
        new MainGame();
    }

    private MainGame() {
        random = new Random(); // Creates a new instance of the Random object
        csi = new WSwingConsoleInterface(); // Creates a new instance of WSwingConsoleInterface.
        map = new Map();

        /**
         * Begin to initialize characters and other initial
         * parts of the game.
         *
         * Character initialization:
         * TODO: Add character initialization description.
         */

        IntPoint prevPos = new IntPoint(1, 1);

        character = new Character("Justin Li", CharacterType.Wizard);
        character.setMinXY(map.getMinX(), map.getMinY());
        character.setMaxXY(map.getMaxX(), map.getMaxY());
        character.setMaxHealth(20);
        character.setFloor(1);
        character.spawn('.');

        LongSword ls = new LongSword();

        character.setItemInHand(ls);

        Entity.entities.add(character);

        // Initializes the main loop to run the game:
        while(true) {

            prevPos = character.getPosition();
            /**
             * Beginning portion of this loop will display all of the
             * entities/game objects on the window itself.
             */

            // Print Entities & Character information:
            Entity.entities.forEach(e ->
                    csi.print(e.getX(), e.getY(), e.getRepresentation(), e.getColor()));
            character.displayInformation();

            csi.refresh();
            /**
             * The next part takes in keyboard input and
             * then processes it using the switch/case statement.
             */
            int key = csi.inkey().code;

            Keys:
            switch(key) {
                case 86: // Up
                    character.move(0, -1);
                    break;
                case 82: // Down
                    character.move(0, 1);
                    break;
                case 64: // Left
                    character.move(-1, 0);
                    break;
                case 67: // Right
                    character.move(1, 0);
                    break;
                case 40: // Space bar
                    Item inHand = character.getItemInHand();
                    if(inHand != null)
                        inHand.useItem();

                    break;
                case 10: // Enter
                    // Creates a new map interface/object when the
                    // player presses the enter on a stair character.
                    if(character.getPrevCharOfMap() == '/') {
                        Entity.entities.removeIf(e -> !(e instanceof Character));
                        csi.cls();
                        map = new Map();
                        character.spawn('/');
                    }
                    break;
                default:
                    break;
            }

            /**
             * Runs the AI of the game (if the character changed position).
             * This will move monsters, spawn monsters, update the
             * character, and spawn more items.
             */
            runAI(character);
        }
    }

    private void runAI(Character c) {
        for(Entity e : Entity.entities) {
            if(e instanceof Monster)
                ((Monster) e).performAI(character);

            if(e.isDead()) {
                Entity.entities.remove(e);
                map.setCharacter(e.getPrevCharOfMap(), e.getX(), e.getY(), e.getPrevColorOfMap());
            }
        }

        // Spawning monsters
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
