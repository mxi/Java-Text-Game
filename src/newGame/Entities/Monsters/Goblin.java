package newGame.Entities.Monsters;

import game.MainGame;
import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Knife;
import sz.csi.ConsoleSystemInterface;

public class Goblin extends Monster {

    public static int INIT_HEALTH = 5;
    public static float EXP_MULTIPLIER = 1.2f;

    public Goblin() {
        setColor(ConsoleSystemInterface.GREEN);
        setRepresentation('G');

        Knife goblinKnife = new Knife();
        setMeleeWeapon(goblinKnife);
        setShield(Shield.Leather);
    }

    @Override
    protected void onMonsterUpgrade() {

    }

    @Override
    public void performAI(Character character) {

        chaseAI(character);
    }
}
