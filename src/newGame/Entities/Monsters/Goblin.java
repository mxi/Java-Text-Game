package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Knife;
import sz.csi.ConsoleSystemInterface;

public class Goblin extends Monster {

    public static int INIT_HEALTH = 5;
    public static float EXP_MULTIPLIER = 1.2f;

    public Goblin(int ilevel) {
        super("Goblin Level " + ilevel, Shield.fromLevel(ilevel), INIT_HEALTH * ilevel, ilevel);
        setColor(ConsoleSystemInterface.GREEN);
        setRepresentation('G');
        setMeleeWeapon(new Knife(this, 5, 28, 4, 1));
    }

    @Override
    public void onKeyPress(int key) {

    }

    @Override
    protected void onMonsterUpgrade() {

    }

    @Override
    protected void onMonsterDowngrade() {

    }

    @Override
    public void performAI(Character character) {
        if(character.getX() - getX() == 0) {
            if(character.getY() > getY() && character.getY() - getY() > 1) {
                moveDown();
            }
            else if(character.getY() > getY() && character.getY() - getY() == 1) {
                getMeleeWeapon().attack(character);
            }
            else if(character.getY() < getY() && character.getY() - getY() > 1) {
                moveUp();
            }
            else if(character.getY() < getY() && character.getY() - getY() == 1){
                getMeleeWeapon().attack(character);
            }
        }
        else if(character.getY() - getY() == 0){
            if(character.getX() > getX() && character.getX() - getX() > 1) {
                moveRight();
            }
            else if(character.getX() > getX() && character.getX() - getX() == 1){
                getMeleeWeapon().attack(character);
            }
            else if(character.getX() < getX() && getX() - character.getX() > 1) {
                moveLeft();
            }
            else if(character.getX() < getX() && getX() - character.getX() == 1){
                getMeleeWeapon().attack(character);
            }
        }
        else {
            if(character.getX() > getX())
                moveRight();
            else if(character.getX() < getX())
                moveLeft();
        }
    }
}
