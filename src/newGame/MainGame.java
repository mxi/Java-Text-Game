package newGame;

import newGame.Entities.Character;
import newGame.Entities.CharacterType;
import newGame.Entities.Entity;
import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Item;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.Monster;
import newGame.Entities.Weapons.LongSword;
import newGame.Entities.Weapons.Melee;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.Random;

public class MainGame {

    private static volatile boolean playing = true;
    private static volatile boolean requestedEnd;

    public static void requestEnd() {
        requestedEnd = true;
    }

    public static void clearCsi(int x, int y, int width, int height) {
        if(csi != null) {
            for(int i = x; i <= x + width; i++) {
                for(int j = y; j <= y + height; j++) {
                    csi.print(i, j, ' ', ConsoleSystemInterface.BLACK);
                    csi.refresh();
                }
            }
        }
    }

    public static Random random; // Random object
    public static ConsoleSystemInterface csi; // Window (console interface)
    public static MapInterface map; // Map of the game.

    private static Character character; // Character of the game.

    public static void main(String[] args) {
        while(playing) {
            new MainGame();
        }

        System.exit(0);
    }

    private MainGame() {
        setup();
        synchronized (this) {
            start();
            try { wait(); }
            catch(InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void setup() {
        if(random == null)
            random = new Random();

        if(csi == null)
            csi = new WSwingConsoleInterface();

        csi.cls();
        csi.refresh();

        requestedEnd = false;
        map = new Map();

        character = new Character("Justin Li", CharacterType.Wizard);
        character.setMaxHealth(25);
        character.setHealth(character.getMaxHealth());
        character.adaptToMap();
        character.setFloor(1);
        character.spawn('.');

        LongSword ls = new LongSword();
        ls.setDamageOutput(4);
        ls.setMaxDurability(125);

        character.setItemsInHand(ls.toInventoryStack());
    }

    private void start() {
        Thread gameThread = new Thread(this::gameLoop);
        gameThread.setDaemon(false);
        gameThread.setName("Game Thread");
        gameThread.start();
    }

    private void gameLoop() {
        synchronized (this) {
            while(true) {
                /**
                 * When end is requested by any object/class within the game
                 * via MainGame.requestEnd() then the thread will be killed,
                 * and will allow the main thread to continue.
                 */
                if(requestedEnd) {
                    break;
                }

                /*
                * Beginning portion of this loop will display all of the
                * entities/game objects on the window itself.
                */

                // Print Entities & Character information:
                map.getEntities().forEach(e ->
                        csi.print(e.getX(), e.getY(), e.getRepresentation(), e.getColor()));

                character.displayInformation();

                csi.refresh();
                /*
                * The next part takes in keyboard input and
                * then processes it using the switch/case statement.
                */
                int key = csi.inkey().code;

                switch(key) {
                    case 86: // Up ('W')
                        character.move(0, -1);
                        break;
                    case 82: // Down ('S')
                        character.move(0, 1);
                        break;
                    case 64: // Left ('A')
                        character.move(-1, 0);
                        break;
                    case 67: // Right ('D')
                        character.move(1, 0);
                        break;
                    case 40: // Use item ('Space bar')
                        InventoryStack<Item> inHand = character.getItemsInHand();
                        if(inHand.isLoneItem() && inHand.getItem() instanceof Melee)
                            inHand.getItem().useItem();

                        break;
                    case 10: // Use item on floor ('Enter')
                        // Creates a new map interface/object when the
                        // player presses the enter on a stair character.
                        if(character.getPrevCharOfMap() == '/') {
                            csi.cls();
                            map = new Map();
                            character.spawn('/');
                        }
                        break;
                    default:
                        System.out.println(key);
                        break;
                }

                /*
                * Runs the AI of the game (if the character changed position).
                * This will move monsters, spawn monsters, update the
                * character, and spawn more items.
                */
                runAI(character);
            }

            // Runs the game over screen:
            runGameOverScreen();

            // Notifies the main thread that this game has finished:
            notify();
        }
    }

    private void runAI(Character c) {
        /*
        map.getEntities().forEach(entity -> {
            if(entity instanceof Monster)
            	if(((Monster) entity).FindMode)
            		((Monster) entity).findAI(character, random.nextInt(5), 10/*((Monster) entity).Persistance);
            	else
            		((Monster) entity).performAI(character);
        });
        */

        for(Entity e : map.getEntities()) {
            if(e instanceof Monster)
                ((Monster) e).performAI(c);
        }

        // Spawning monsters
        if(random.nextInt(101) <= Goblin.SPAWN_CHANCE && map.getEntityCountOf(Goblin.NAME) < Goblin.LIMIT) {
            Goblin goblin = new Goblin();
            goblin.setLevel(1);
            goblin.adaptToMap();
            goblin.spawn('.');
            goblin.getMeleeWeapon().setDamageOutput(1);
        }
    }

    private void runGameOverScreen() {
        // Clears the screen for displaying
        // end-game message:
        csi.cls();

        String gameOver = "Game Over!";

        int gameOverX = (map.getMapWidth() / 2) - (int) (Math.floor(gameOver.length()) / 2);
        int gameOverY = map.getMapHeight() / 2;

        csi.print(gameOverX, gameOverY, gameOver, ConsoleSystemInterface.RED);
        csi.print(gameOverX, gameOverY + 2, "[P] Play Again", ConsoleSystemInterface.WHITE);
        csi.print(gameOverX, gameOverY + 3, "[L] Leaderboards", ConsoleSystemInterface.WHITE);
        csi.print(gameOverX, gameOverY + 4, "[Q] Quit", ConsoleSystemInterface.WHITE);

        // Game over screen:
        GameOverScreen:
        while(true) {
            csi.refresh();
            int key = csi.inkey().code;

            switch(key) {
                case 79: // 'P'
                    break GameOverScreen;
                case 75: // 'L'
                    break GameOverScreen;
                case 80: // 'Q'
                    // TODO: Add saving logic later on.
                    playing = false;
                    csi.print(1, map.getMapHeight(), "Saving...", ConsoleSystemInterface.WHITE);
                    csi.refresh();
                    break GameOverScreen;
                default:
                    String invalidKey = "Invalid key pressed";
                    int invalidX = (map.getMapWidth() / 2) - (int) (Math.floor(invalidKey.length()) / 2);
                    int invalidY = gameOverY + 6;

                    csi.print(invalidX, invalidY, invalidKey, ConsoleSystemInterface.WHITE);
                    break;
            }
        }
    }
}
