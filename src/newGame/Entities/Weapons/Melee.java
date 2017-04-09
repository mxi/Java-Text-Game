package newGame.Entities.Weapons;

import newGame.Entities.Entity;
import newGame.Entities.Item;

import java.util.List;

public class Melee extends Item {

    private int damageOutput;
    private int damageBonus;
    private int swingRange;
    private boolean swingWeapon;

    public Melee(String iname, int idurability, int idegradation, int ihousing, int idamageOutput, int idamageBonus, int ilevel) {
        super(iname, idurability, idegradation, ihousing, ilevel);
        damageOutput = idamageOutput;
        damageBonus = idamageBonus;
        swingRange = 2;
        swingWeapon = true;
    }

    public int getSwingRange() {
        return swingRange;
    }

    public void setSwingRange(int swingRange) {
        this.swingRange = swingRange;
    }

    public boolean isSwingWeapon() {
        return swingWeapon;
    }

    public void setSwingWeapon(boolean swingWeapon) {
        this.swingWeapon = swingWeapon;
    }

    public int getDamageOutput() {
        return damageOutput;
    }

    public void setDamageOutput(int damageOutput) {
        this.damageOutput = damageOutput;
    }

    public int getDamageOutputBonus() {
        return damageBonus;
    }

    public void setDamageOutputBonus(int damageBonus) {
        this.damageBonus = damageBonus;
    }

    public void upgradeDamageOutput() {
        damageOutput += 5;
    }

    public void upgradeDamageOutputBonus() {
        damageBonus += 5;
    }

    public void attack(Entity entity) {
        entity.damage((int) (damageOutput * (1 + damageBonus / 100.0)));
    }

    public void swing(List<Entity> entities) {
        if(!swingWeapon)
            return;

        entities.forEach(entity -> {
            if(entity.distance(getX(), getY()) <= swingRange)
                attack(entity);
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeDamageOutput();
        upgradeDamageOutputBonus();
    }

}
