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
        if(character.getX() - getX() == 0) {
            if(character.getY() > getY() && character.getY() - getY() > 1) {
                move(0, 1);
            }
            else if(character.getY() > getY() && character.getY() - getY() == 1) {
                getMeleeWeapon().attack(character);
            }
            else if(character.getY() < getY() && character.getY() - getY() > 1) {
                move(0, -1);
            }
            else if(character.getY() < getY() && character.getY() - getY() == 1){
                getMeleeWeapon().attack(character);
            }
        }
        else if(character.getY() - getY() == 0){
            if(character.getX() > getX() && character.getX() - getX() > 1) {
                move(1, 0);
            }
            else if(character.getX() > getX() && character.getX() - getX() == 1){
                getMeleeWeapon().attack(character);
            }
            else if(character.getX() < getX() && getX() - character.getX() > 1) {
                move(-1, 0);
            }
            else if(character.getX() < getX() && getX() - character.getX() == 1){
                getMeleeWeapon().attack(character);
            }
        }
        else {
            if(character.getX() > getX())
                move(-1, 0);
            else if(character.getX() < getX())
                move(1, 0);
        }
    }
}
