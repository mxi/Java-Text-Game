package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Item;

import java.util.List;

public abstract class Melee extends Item {

    private Entity owner;
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

        super.addOnUpgradeEvent(() -> {
            upgradeDamageOutput();
            upgradeDamageOutputBonus();
        });
    }

    public Entity getOwner() {
        return this.owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
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

        if(entity.isDead() && this.owner instanceof Character)
            rewardForKill((Character) this.owner);
        else
            rewardForHit((Character) this.owner);
    }

    public void swing(List<Entity> entities) {
        if(!swingWeapon)
            return;

        entities.forEach(entity -> {
            if(entity.distance(getX(), getY()) <= swingRange)
                attack(entity);
        });
    }

    protected abstract void rewardForKill(Character entity);

    protected abstract void rewardForHit(Character entity);
}
