package newGame.Entities.Orbs;

import newGame.Entities.Item;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

/**
 * Exp orbs will give free exp to
 * the character.
 *
 * @author Max
 */
public class ExpOrb extends Item {

    /**
     * The amount of exp that will be given
     * to the character on use.
     */
    private float expOutput;
    /**
     * Default exp orb constructor that will
     * initialize the representable
     * class and the exp output
     * variable.
     */
    public ExpOrb() {
        setRepresentation('+');
        setColor(ConsoleSystemInterface.YELLOW);
        setName("Exp Orb");
        expOutput = 3; // default exp output
    }

    /**
     * Constructor that will set
     * the expOutput to whatever the
     * caller desires.
     * @param expOut Exp output value.
     */
    public ExpOrb(float expOut) {
        setRepresentation('+');
        setColor(ConsoleSystemInterface.YELLOW);
        setName("Exp Orb");
        expOutput = expOut;
    }

    /**
     * Actions that will occur when this item
     * is used.
     */
    @Override
    protected void onItemUse() {
        MainGame.character.addExp(expOutput);
    }
}
