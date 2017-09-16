package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.LongBow;
import sz.csi.ConsoleSystemInterface;

public class Sniper extends Monster {

	public static final String NAME = "Sniper";
    public static final int SPAWN_CHANCE = 1;
    public static final int LIMIT = 2;

    public Sniper() {
		setColor(ConsoleSystemInterface.BROWN);
	    setName(NAME);
	    setRepresentation('S');
	
	    LongBow SniperBow = new LongBow();
	    SniperBow.setMaxDurability(999);
	    SniperBow.setDamageOutput(3);
	    setMeleeWeapon(SniperBow);
	    setShield(Shield.Glass);
	    setExpRewardMax(40);
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
