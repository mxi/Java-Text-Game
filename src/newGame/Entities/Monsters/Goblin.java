package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Knife;
import newGame.IntPoint;
import sz.csi.ConsoleSystemInterface;

public class Goblin extends Monster {

    public static final String NAME = "Goblin";
    public static final int SPAWN_CHANCE = 15;
    public static final int LIMIT = 4;

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
    protected void onMonsterUpgrade() {

    }

    @Override
    public void performAI(Character character) {
        if(!inSight(character))
            findAI(character);
        else
            chaseAI(character);

        IntPoint cpos = character.getPosition();
        if(cpos.isEquivalentTo(previewMove(0, -1))
                || cpos.isEquivalentTo(previewMove(1, 0))
                || cpos.isEquivalentTo(previewMove(0, 1))
                || cpos.isEquivalentTo(previewMove(-1, 0)))
        {
            getMeleeWeapon().useItem();
        }
    }
}
