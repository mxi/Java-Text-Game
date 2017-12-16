package newGame;

import java.util.Random;

import newGame.Animations.Animations;
import newGame.Entities.Character;
import newGame.Entities.CharacterType;
import newGame.Entities.Shield;
import newGame.Entities.Monsters.Archer;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.HobGoblin;
import newGame.Entities.Monsters.Monster;
import newGame.Entities.Monsters.Sniper;
import newGame.Entities.Orbs.ExpOrb;
import newGame.Entities.Orbs.HealthOrb;
import newGame.Entities.Weapons.Knife;
import newGame.Entities.Weapons.LongBow;
import newGame.Entities.Weapons.LongSword;
import newGame.Entities.Weapons.ShortBow;
import newGame.Mapping.Map;
import newGame.Mapping.MapInterface;
import newGame.Mapping.Tile;
import newGame.menus.*;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

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
                    try { csi.print(i, j, ' ', ConsoleSystemInterface.BLACK); }
                    catch(ArrayIndexOutOfBoundsException e) { }
                    csi.refresh();
                }
            }
        }
    }

    public static void centerPrintHorizontal(int width, int y, String text, int color) {
        int x = (width / 2) - (int) (Math.floor(text.length()) / 2);
        csi.print(x, y, text, color);
    }

    public static volatile Random random = new Random(); // Random object
    public static volatile ConsoleSystemInterface csi = new WSwingConsoleInterface(); // Window (console interface)
    public static volatile MapInterface map; // Map of the game.
    public static volatile Character character; // Character of the game.
    public static final int MOVE_SLEEP_TIME = 50; // How many milliseconds to wait before the player can move again.

    public static void main(String[] args) {
        /*for(int i = 7;; i++)
    	{
    		random.setSeed(i);
    		System.out.println(i);
    		new Map();
    		csi.refresh();
    		csi.waitKey(10);
    		csi.cls();
    	}*/

<<<<<<< HEAD
//        Animation anim = new Animation();
//        anim.setMillisecondsPerTick(500);
//        anim.setDurationInMilliseconds(5000);
//        anim.start();

        String user = System.getProperty("user.name");
        Logger.newLog("C:/Users/" + user + "/Desktop/Java-Text-Game Logs/", "jtg_");
        Logger.info("Initialized logger.");
=======
        //String user = System.getProperty("user.name");
        //Logger.newLog("C:/Users/" + user + "/Desktop/Java-Text-Game Logs/", "jtg_");
        //Logger.info("Initialized logger.");
>>>>>>> 1f14ec04ef7363fa13ef19750e3b49377e28798e
        while(playing) {
             new MainGame();
        }

        //Logger.info("Application Terminated: 0");
        //System.exit(0);
    }

    private MainGame() {
        synchronized (this) {
            setup();
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
        map = fetchMap(1);
        map.getMapBuffer().scatter(new Knife(), Tile.SPACE, 1, 1, 4);
        map.getMapBuffer().scatter(new LongSword(), Tile.SPACE, 1, 1, 1);
        map.getMapBuffer().scatter(new LongBow(), Tile.SPACE, 1, 1, 1);
        map.getMapBuffer().scatter(new ShortBow(), Tile.SPACE, 1, 1, 3);

        character = new Character("Player", CharacterType.Wizard);
        character.setMaxHealth(25);
        character.setHealth(character.getMaxHealth());
        character.adaptToMap();
        character.setFloor(1);
        character.setShield(Shield.Leather);
        character.spawn(Tile.SPACE);

        final String characterNameTextField = "cname";
        /*
        Menu m = new Menu(40, 19, false);
        m.setTitle("Epic Window");
        m.setShown(true);
        m.setOnClose(() -> {
            TextField nameInput = (TextField) m.getComponentByName(characterNameTextField);
            if(nameInput.getText().length() == 0)
                character.setName("Player");
            else
                character.setName(nameInput.getText());
        });

        Label cNameLabel = new Label("Enter character name:", 9, 3);

        TextField cName = new TextField(14, 4, 8);
        cName.setName(characterNameTextField);
        cName.setMaxTextLength(8);

        Button confirmButton = new Button("Continue", 13, 12);
        confirmButton.setOnAction(m::close);

        m.addAllComponents(cNameLabel, cName, confirmButton);
        */
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
                if(Menu.hasShownMenus()) {
                    MainGame.csi.cls();
                    Menu.updateShown(-1);
                    int key = csi.inkey().code;
                    Menu.updateShown(key);
                    continue;
                }
                /*
                 * When end is requested by any object/class within the game
                 * via MainGame.requestEnd() then the thread will be killed,
                 * and will allow the main thread to continue.
                 */
                if(requestedEnd) {
                    break;
                }

                /*
                 * Decides whether the 'performAI' function
                 * gets called or not.
                 */
                boolean performAi = true;

                /*
                * Beginning portion of this loop will display all of the
                * entities/game objects on the window itself.
                */

                if(character.isDead())
                    break;

                // Renders the world:
                map.render(csi);
                character.displayInformation();

                map.getEntities().forEach(e -> {
                    // e.debugDrawPath(e.getPosition(), character.getPosition());
                });

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
                        character.useItemInHand();
                        break;
                    case 10: // Use item on floor ('Enter')
                        // Creates a new map interface/object when the
                        // player presses the enter on a stair character.
                        if(map.getTile(character.getPosition()).equalsTo(Tile.STAIR)) {
                            csi.cls();
                            if(Map.maps.containsKey(map.getFloor() + 1))
                                map = Map.maps.get(map.getFloor() + 1);
                            else
                                map = fetchMap(map.getFloor() + 1);
                            character.spawn(Tile.STAIR);
                        }
                        // heals the player a certain amount
                        // of health
                        if(map.getTile(character.getPosition()).equalsTo(Tile.HEALTILE)) {
                            character.heal(25);
                        }
                        break;
                    case 30: // Escape key
                        if(map.getTile(character.getPosition()).equalsTo(Tile.STAIR)
                                && Map.maps.containsKey(map.getFloor() - 1)) {
                            csi.cls();
                            map = Map.maps.get(map.getFloor() - 1);
                            character.spawn(Tile.STAIR);
                        }
                        break;
                    case 80: // Drop in hand ('Q')
                        character.dropItemInHand();
                        break;
                    case 68: // Inventory1 ('E')
                        character.tradeItemInHand(0);
                        break;
                    case 81: // Inventory2 ('R')
                        character.tradeItemInHand(1);
                        break;
                    case 83: // Inventory3 ('T')
                        character.tradeItemInHand(2);
                        break;
                    case 79: // Pickup item ('P')
                        character.pickupItemOnGround();
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
                if(performAi) {
                    runAI(character);
                }

                try {
                    Thread.sleep(MOVE_SLEEP_TIME);
                }
                catch(InterruptedException ignore) {

                }
            }

            // Runs the game over screen:
            runGameOverScreen();

            // Notifies the main thread that this game has finished:
            notify();
        }
    }

    private MapInterface fetchMap(int floor) {
        final Map m = new Map(floor);
        m.setRenderingLightSource(false);
        m.setLightSourceRadius(5.8f);
        map = m;
        scatterMaterial(character == null ? 1 : character.getLevel());
        return map;
    }
    
    private void scatterMaterial(int characterLevel) {
        Knife calcKnives = new Knife();
        calcKnives.setDamageOutput(characterLevel + calcKnives.getDamageOutput());
        LongSword calcLSword = new LongSword();
        calcLSword.setDamageOutput(characterLevel + calcLSword.getDamageOutput());
//        LongBow calcLBow = new LongBow();
//        calcLBow.setDamageOutput(characterLevel + calcLBow.getDamageOutput());
        ShortBow calcSBow = new ShortBow();
        calcSBow.setDamageOutput(characterLevel + calcSBow.getDamageOutput());
//        FiftyCaliberMachineGun fiftyCal = new FiftyCaliberMachineGun();
//        map.getMapBuffer().scatter(fiftyCal, Tile.SPACE, 1, 1, 5);
        map.getMapBuffer().scatter(calcKnives, Tile.SPACE, 1, 1, 3);
        map.getMapBuffer().scatter(calcLSword, Tile.SPACE, 1, 1, 2);
//        map.getMapBuffer().scatter(calcLBow, Tile.SPACE, 1, 1, 2);
        map.getMapBuffer().scatter(calcSBow, Tile.SPACE, 1, 1, 3);
<<<<<<< HEAD
        map.getMapBuffer().scatter(new HealthOrb(10), Tile.SPACE, 1, 4, 2);
        map.getMapBuffer().scatter(new ExpOrb(characterLevel * 50), Tile.SPACE, 1, 6, 5);
=======
        map.getMapBuffer().scatter(new HealthOrb(characterLevel * 3 + random.nextInt(5)), Tile.SPACE, 1, 4, 2);
        map.getMapBuffer().scatter(new ExpOrb(characterLevel * 3 + random.nextInt(5)), Tile.SPACE, 1, 6, 5);
>>>>>>> 1f14ec04ef7363fa13ef19750e3b49377e28798e
    }

    private void runAI(Character c) {
        map.getEntities().forEach(entity -> {
            if(entity instanceof Monster) {
                final Monster mon = (Monster) entity;
                mon.performAI(character);
            }
        });

        // Spawning monsters
        if(shouldSpawnMob(Goblin.SPAWN_CHANCE, map.getEntityCountOf(Goblin.NAME), Goblin.LIMIT)) {
            Goblin goblin = new Goblin();
            goblin.adaptToMap();
            goblin.spawn(Tile.SPACE);
            goblin.getMeleeWeapon().setDamageOutput(c.getLevel() * 2);
            goblin.setHealth(c.getLevel() * 2);
        }
        if(shouldSpawnMob(HobGoblin.SPAWN_CHANCE, map.getEntityCountOf(HobGoblin.NAME), HobGoblin.LIMIT)) {
            HobGoblin hobgoblin = new HobGoblin();
            hobgoblin.adaptToMap();
            hobgoblin.spawn(Tile.SPACE);
            hobgoblin.getMeleeWeapon().setDamageOutput(c.getLevel() * 2 + 2);
            hobgoblin.setHealth(c.getLevel() * 2 + 2);
        }
        if(shouldSpawnMob(Archer.SPAWN_CHANCE, map.getEntityCountOf(Archer.NAME), Archer.LIMIT)) {
            Archer archer = new Archer();
            archer.adaptToMap();
            archer.spawn(Tile.SPACE);
            archer.getMeleeWeapon().setDamageOutput(c.getLevel() * 2 + 1);
            archer.setHealth(c.getLevel() * 2);
        }
//        if(shouldSpawnMob(Sniper.SPAWN_CHANCE, map.getEntityCountOf(Sniper.NAME), Sniper.LIMIT)) {
//            Sniper sniper = new Sniper();
//            sniper.adaptToMap();
//            sniper.spawn(Tile.SPACE);
//            sniper.getMeleeWeapon().setDamageOutput(c.getLevel() * 2 + 1);
//            sniper.setHealth(c.getLevel() * 2);
//        }
    }

    /**
     * Checks whether a monster should be able to spawn or not.
     * @param schance Spawn chance (out of 100)
     * @param maxec Maximum amount of the monster per map.
     * @return Whether the mob should spawn or not.
     */
    private boolean shouldSpawnMob(int schance, int countinmap, int maxec) {
        return random.nextInt(100) + 1 <= schance && countinmap < maxec;
    }

    private void runGameOverScreen() {
        // Clears the screen for displaying
        // end-game message:
        csi.cls();

        String gameOver = "Game Over!";
        int gameOverY = map.getMapHeight() / 2;

        centerPrintHorizontal(map.getMapWidth(), gameOverY, gameOver, ConsoleSystemInterface.RED);
        centerPrintHorizontal(map.getMapWidth(), gameOverY + 3, "[P]Play Again", ConsoleSystemInterface.WHITE);
        centerPrintHorizontal(map.getMapWidth(), gameOverY + 5, "[L]Leaderboards", ConsoleSystemInterface.WHITE);
        centerPrintHorizontal(map.getMapWidth(), gameOverY + 7, "[Q]Quit", ConsoleSystemInterface.WHITE);

        // Game over screen:
        GameOverScreen:
        while(true) {
            csi.refresh();
            int key = csi.inkey().code;

            clearCsi(1, gameOverY + 10, 100, 30);

            switch(key) {
                case 79: // 'P'
                    break GameOverScreen;
                case 75: // 'L'
                    csi.print(1, map.getMapHeight() + 5, "Leaderboards are not a feature yet.");
                    break;
                case 80: // 'Q'
                    // TODO: Add saving logic later on.
                    playing = false;
                    csi.print(1, map.getMapHeight() + 5, "Saving...", ConsoleSystemInterface.WHITE);
                    csi.refresh();
                    break GameOverScreen;
                default:
                    centerPrintHorizontal(map.getMapWidth(), gameOverY + 10, "Invalid key pressed", ConsoleSystemInterface.WHITE);
                    break;
            }
        }
    }
}
