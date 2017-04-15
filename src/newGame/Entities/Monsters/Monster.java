package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.EntityAttributes;
import newGame.Entities.Weapons.Melee;
import newGame.Map;

public abstract class Monster extends Entity {

    private EntityAttributes.Shield shield;
    private Melee meleeWeapon; // Melee weapon
    // private Range rangeWeapon; // Range weapon

    public Monster(String iname, EntityAttributes.Shield ishield, int ihealth, int ilevel) {
        super(iname, ihealth, ilevel);
        shield = ishield;
        meleeWeapon = null;
    }

    public EntityAttributes.Shield getShield() {
        return shield;
    }

    public void setShield(EntityAttributes.Shield shield) {
        this.shield = shield;
    }

    public Melee getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(Melee meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    protected abstract void performAI(Character character, Map map);

    // TODO: add range weapon getter and setter.
}
