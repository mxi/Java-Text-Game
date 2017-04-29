package newGame.Entities.Monsters;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Melee;
import newGame.Map;

public abstract class Monster extends Entity {

    private Shield shield;
    private Melee meleeWeapon;

    public Monster() {
        super();
    }

    public Monster(String iname, Shield ishield, int ihealth, int ilevel) {
        super(iname, ihealth, ilevel);
        setRepresentation('M');
        shield = ishield;
        meleeWeapon = null;
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
        return false;
    }

    public boolean hasMeleeWeapon() {
        return meleeWeapon != null;
    }

    @Override
    public void onEntityUpgrade() {
        onMonsterUpgrade();
    }

    @Override
    public void onEntityDowngrade() {
        onMonsterDowngrade();
    }

    protected abstract void onMonsterUpgrade();

    protected abstract void onMonsterDowngrade();

    public abstract void performAI(Character character, Map map);

    // TODO: add range weapon getter and setter.
}
