package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Knife;
import newGame.Entities.Weapons.LongSword;
import sz.csi.ConsoleSystemInterface;

public class HobGoblin extends Monster{
	public static final String NAME = "Hobgoblin";
    public static final int SPAWN_CHANCE = 2;
    public static final int LIMIT = 6;

    public HobGoblin() {
        setColor(ConsoleSystemInterface.RED);
        setName(NAME);
        setRepresentation('H');

        LongSword HobGoblinLSword = new LongSword();
        HobGoblinLSword.setMaxDurability(999);
        HobGoblinLSword.setDamageOutput(2);
        setMeleeWeapon(HobGoblinLSword);
        setShield(Shield.Plastic);
        setExpRewardMax(25);
        setExpRewardMin(15);
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
