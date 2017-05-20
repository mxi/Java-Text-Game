package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Melee;

public abstract class Monster extends Entity {

    private Shield shield;
    private Melee meleeWeapon;

    public Monster() {

    }

    public Shield getShield() {
        return shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public Melee getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(Melee meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public boolean hasRangeWeapon() {
        // TODO: Work on range weapons some time
        return false;
    }

    public boolean hasMeleeWeapon() {
        return meleeWeapon != null;
    }

    @Override
    public void onEntityUpgrade() {
        onMonsterUpgrade();
    }

    protected abstract void onMonsterUpgrade();

    public abstract void performAI(Character character);
}
