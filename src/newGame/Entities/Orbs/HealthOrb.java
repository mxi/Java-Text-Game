package newGame.Entities.Orbs;

import newGame.Entities.Item;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

/**
 * Health orbs will be able to be picked up
 * by the player and heal them.
 *
 * @author Max
 */
public class HealthOrb extends Item {

    /**
     * The int amount of health that will be
     * given to the player if they use it.
     */
    private int healthOutput;
    /**
     * Default constructor for HealthOrb;
     * initializes everything to default
     * as defined by this constructor.
     */
    public HealthOrb() {
        setRepresentation('+');
        setColor(ConsoleSystemInterface.DARK_RED);
        setName("Health Orb");
        healthOutput = 3; // default healthOutput amount.
    }

    /**
     * Constructor that will set the
     * health output to whatever
     * the caller desires.
     * @param healthOut Health output value.
     */
    public HealthOrb(int healthOut) {
        setRepresentation('+');
        setColor(ConsoleSystemInterface.DARK_RED);
        setName("Health Orb");
        healthOutput = healthOut;
    }

    /**
     * Actions that will occur when the character
     * utilizes this item.
     */
    @Override
    protected void onItemUse() {
        MainGame.character.heal(healthOutput);
    }
}
