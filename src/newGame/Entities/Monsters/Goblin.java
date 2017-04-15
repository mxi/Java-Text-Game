package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.EntityAttributes;
import newGame.Map;
import sz.csi.ConsoleSystemInterface;

public class Goblin extends Monster {

    public static int INIT_HEALTH = 5;
    public static float EXP_MULTIPLIER = 1.2f;

    public Goblin(int ilevel) {
        super("Goblin Level " + ilevel, EntityAttributes.Shield.fromLevel(ilevel), INIT_HEALTH * ilevel, ilevel);
        setColor(ConsoleSystemInterface.GREEN);
        setRepresentation('G');

        addOnUpgradeEvent(() -> setExpUntilLevelUp((int) (getExpUntilLevelUp() * EXP_MULTIPLIER)));
    }

    @Override
    public void performAI(Character character, Map map) {

    }
}
