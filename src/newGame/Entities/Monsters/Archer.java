package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.ShortBow;
import sz.csi.ConsoleSystemInterface;

public class Archer extends Monster{
	public static final String NAME = "Archer";
    public static final int SPAWN_CHANCE = 3;
    public static final int LIMIT = 6;

    public Archer() {
        setColor(ConsoleSystemInterface.BLUE);
        setName(NAME);
        setRepresentation('A');

        ShortBow ArcherBow = new ShortBow();
        ArcherBow.setMaxDurability(999);
        ArcherBow.setDamageOutput(2);
        setMeleeWeapon(ArcherBow);
        setShield(Shield.Leather);
        setExpRewardMax(20);
        setExpRewardMin(5);
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
