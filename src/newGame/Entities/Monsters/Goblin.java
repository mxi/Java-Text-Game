package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.EntityAttributes;
import newGame.Map;

public class Goblin extends Monster {

    public static int INIT_HEALTH = 5;

    public Goblin(int ilevel) {
        super("Goblin Level " + ilevel, EntityAttributes.Shield.fromLevel(ilevel), INIT_HEALTH * ilevel, ilevel);
    }

    @Override
    protected void performAI(Character character, Map map) {

    }
}
