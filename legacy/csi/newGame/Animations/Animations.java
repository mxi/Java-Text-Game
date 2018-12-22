package newGame.Animations;

import newGame.Entities.Representable;
import newGame.IntPoint;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

/**
 * This class contains functions that
 * would run animations based on the screen.
 */
public class Animations {

    private static final int ENTITY_DEATH_TIMES = 5; // The amount of times to flash dead entity.
    private static final int ENTITY_DEATH_FLASH_TIME = 100; // The time to wait before each flash/change of color
    private static final int ENTITY_DEATH_COLOR = ConsoleSystemInterface.WHITE; // Color to flash to

    private static final int ENTITY_DAMAGE_TIMES = 2; // The amount of times to flash dead entity.
    private static final int ENTITY_DAMAGE_FLASH_TIME = 25; // The time to wait before each flash/change of color
    private static final int ENTITY_DAMAGE_COLOR = ConsoleSystemInterface.BLACK; // Color to flash to

    private static final int ENTITY_HEAL_TIMES = 3; // The amount of times the circle of health is rendered
    private static final int ENTITY_HEAL_EXPAND_TIME = 75; // The time it takes for the circle to expand in milliseconds
    private static final int ENTITY_HEAL_COLOR = ConsoleSystemInterface.LEMON; // Color of the heal circle.
    private static final char ENTITY_HEAL_CHAR = '+'; // The character used to represent the healing circle.

    private static final int SWORD_SWING_TIME = 50; // The time it takes for each sword direction to display.
    private static final int SWORD_SWING_COLOR = ConsoleSystemInterface.WHITE; // Color of the swords swing

    /**
     * Runs an entity death animation.
     * @param rep The representation of the enemy.
     * @param x The X value of the location of the death.
     * @param y The Y value of the location of the death.
     */
    public static void entityDeath(Representable rep, int x, int y) {
        for(int i = 0; i < ENTITY_DEATH_TIMES; i++) {
            int color = i % 2 == 0 ? rep.getColor() : ENTITY_DEATH_COLOR;
            MainGame.csi.print(x, y, rep.getRepresentation(), color);
            MainGame.csi.refresh();
            try {
                Thread.sleep(ENTITY_DEATH_FLASH_TIME);
            }
            catch(InterruptedException ignore) {

            }
        }
    }

    /**
     * Flashes onto the screen signaling that the
     * entity has been damaged.
     * @param rep The representation of the entity to flash.
     * @param x The X value of the position of the entity on screen.
     * @param y The Y value of the position of the entity on screen.
     */
    public static void entityDamage(Representable rep, int x, int y) {
        for(int i = 0; i < ENTITY_DAMAGE_TIMES; i++) {
            int color = i % 2 == 0 ? rep.getColor() : ENTITY_DAMAGE_COLOR;
            MainGame.csi.print(x, y, rep.getRepresentation(), color);
            MainGame.csi.refresh();
            try {
                Thread.sleep(ENTITY_DAMAGE_FLASH_TIME);
            }
            catch(InterruptedException ignore) {

            }
        }
    }

    /**
     * Runs a healing animations when a
     * player receives health back.
     * @param x The X value of the position to render the circle of health around.
     * @param y The Y value of the position to render the circle of health around.
     */
    public static void entityHeal(int x, int y) {
        for(int i = 1; i <= ENTITY_HEAL_TIMES; i++) {
            MainGame.map.render(MainGame.csi);
            MainGame.character.displayInformation();
            strokeCircle(x, y, i, ENTITY_HEAL_CHAR, ENTITY_HEAL_COLOR);
            try {
                Thread.sleep(ENTITY_HEAL_EXPAND_TIME);
            }
            catch(InterruptedException ignore) {

            }
        }
    }

    /**
     * Performs a sword swing animation in the specified
     * direction.
     * @param direction The direction to swing the sword in.
     * @param x The X value of the location of the owner.
     * @param y The Y value of the location of the owner.
     */
    public static void swordSwing(int direction, int x, int y) {
        boolean leftToRight = MainGame.random.nextInt(2) == 0;
        switch(direction) {
            case 0:
                swordSwingMech(new IntPoint(x - 1, y), new IntPoint(x - 1, y - 1), new IntPoint(x, y - 1),
                        '-', '\\', '|', leftToRight);
                break;
            case 1:
                swordSwingMech(new IntPoint(x - 1, y - 1), new IntPoint(x, y - 1), new IntPoint(x + 1, y - 1),
                        '\\', '|', '/', leftToRight);
                break;
            case 2:
                swordSwingMech(new IntPoint(x, y - 1), new IntPoint(x + 1, y - 1), new IntPoint(x + 1, y),
                        '|', '/', '-', leftToRight);
                break;
            case 3:
                swordSwingMech(new IntPoint(x - 1, y + 1), new IntPoint(x - 1, y), new IntPoint(x, y - 1),
                        '/', '-', '\\', leftToRight);
                break;
            case 4:
                swordSwingMech(new IntPoint(x + 1, y - 1), new IntPoint(x + 1, y), new IntPoint(x + 1, y + 1),
                        '/', '-', '\\', leftToRight);
                break;
            case 5:
                swordSwingMech(new IntPoint(x, y + 1), new IntPoint(x - 1, y + 1), new IntPoint(x - 1, y),
                        '|', '/', '-', leftToRight);
                break;
            case 6:
                swordSwingMech(new IntPoint(x + 1, y + 1), new IntPoint(x, y + 1), new IntPoint(x - 1, y + 1),
                        '\\', '|', '/', leftToRight);
                break;
            case 7:
                swordSwingMech(new IntPoint(x + 1, y), new IntPoint(x + 1, y + 1), new IntPoint(x, y + 1),
                        '-', '\\', '|', leftToRight);
                break;
            default:
                // Do nothing
                break;
        }
    }

    /**
     * A utility function to provide the swing
     * animation as one function.
     */
    private static void swordSwingMech(IntPoint left, IntPoint mid, IntPoint right,
                   char leftRep, char midRep, char rightRep, boolean leftToRight) {
        MainGame.map.render(MainGame.csi);
        MainGame.character.displayInformation();
        if(leftToRight)
            MainGame.csi.print(left.getX(), left.getY(), leftRep, SWORD_SWING_COLOR);
        else
            MainGame.csi.print(right.getX(), right.getY(), rightRep, SWORD_SWING_COLOR);
        MainGame.csi.refresh();
        try {
            Thread.sleep(SWORD_SWING_TIME);
        }
        catch(InterruptedException ignore) {

        }

        MainGame.map.render(MainGame.csi);
        MainGame.character.displayInformation();
        MainGame.csi.print(mid.getX(), mid.getY(), midRep, SWORD_SWING_COLOR);
        MainGame.csi.refresh();
        try {
            Thread.sleep(SWORD_SWING_TIME);
        }
        catch(InterruptedException ignore) {

        }

        MainGame.map.render(MainGame.csi);
        MainGame.character.displayInformation();
        if(leftToRight)
            MainGame.csi.print(right.getX(), right.getY(), rightRep, SWORD_SWING_COLOR);
        else
            MainGame.csi.print(left.getX(), left.getY(), leftRep, SWORD_SWING_COLOR);
        MainGame.csi.refresh();
        try {
            Thread.sleep(SWORD_SWING_TIME * 5);
        }
        catch(InterruptedException ignore) {

        }
    }

    /**
     * Strokes a circle of a specified radius around
     * a specified location.
     * @param x The X value of the position to stroke the circle around.
     * @param y The Y value of the position to stroke the circle around.
     * @param radius The radius of the circle.
     * @param rep The representation of the circle.
     * @param color The color of the circle.
     */
    private static void strokeCircle(int x, int y, int radius, char rep, int color) {
        double strokeRange = .3f;
        for(int ix = x - radius; ix <= x + radius; ix++) {
            for(int iy = y - radius; iy <= y + radius; iy++) {
                double distance = Math.sqrt( Math.pow(x - ix, 2) + Math.pow(y - iy, 2) );
                if(distance > radius - strokeRange && distance < radius + strokeRange) {
                    try {
                        MainGame.csi.print(ix, iy, rep, color);
                    }
                    catch(Exception ignore) {
                        // Ran out of bounds in the printing.
                    }
                }
            }
        }
        MainGame.csi.refresh();
    }
}
