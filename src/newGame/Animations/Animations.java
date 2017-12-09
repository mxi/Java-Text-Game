package newGame.Animations;

import newGame.Entities.Representable;
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
    private static final int ENTITY_DAMAGE_COLOR = ConsoleSystemInterface.WHITE; // Color to flash to

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
}
