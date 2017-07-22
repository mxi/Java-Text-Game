package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Knife;
import sz.csi.ConsoleSystemInterface;

public class Goblin extends Monster {

    public static final String NAME = "Goblin";
    public static final int SPAWN_CHANCE = 5;
    public static final int LIMIT = 6;

    public Goblin() {
        setColor(ConsoleSystemInterface.GREEN);
        setName(NAME);
        setRepresentation('G');

        Knife goblinKnife = new Knife();
        goblinKnife.setMaxDurability(999);
        goblinKnife.setDamageOutput(1);
        setMeleeWeapon(goblinKnife);
        setShield(Shield.Leather);
    }

    @Override
    public void performAI(Character character) {
        if(distance(character) <= getMeleeWeapon().getRange()) {
            getMeleeWeapon().useItem();
        }
        if(!inSight(character))
            findAI(character);
        else
            chaseAI(character);
    }
}
