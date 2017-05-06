package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Item;

import java.util.List;

public abstract class Melee extends Item {

    private Entity owner;
    private int damageOutput;
    private int swingRange;
    private boolean swingWeapon;

    private int expRewardForKill;
    private int expRewardForHit;

    public Melee() {
        super();
        setName("Melee");
        swingRange = 2;
        swingWeapon = false;

        expRewardForKill = 32;
        expRewardForHit = 3;
    }

    public Melee(Entity iowner, String iname, int idurability, int idegradation, int ihousing, int idamageOutput, int ilevel) {
        super(iname, idurability, idegradation, ihousing, ilevel);
        owner = iowner;
        damageOutput = idamageOutput;
        swingRange = 2;
        swingWeapon = false;

        expRewardForKill = 32;
        expRewardForHit = 3;
    }

    public int getExpRewardForKill() {
        return expRewardForKill;
    }

    public void setExpRewardForKill(int expRewardForKill) {
        this.expRewardForKill = expRewardForKill;
    }

    public int getExpRewardForHit() {
        return expRewardForHit;
    }

    public void setExpRewardForHit(int expRewardForHit) {
        this.expRewardForHit = expRewardForHit;
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

    public void upgradeDamageOutput() {
        damageOutput += 5;
    }

    public void attack(Entity entity) {
        entity.damage(damageOutput);

        if(entity.isDead() && owner instanceof Character)
            rewardForKill((Character) owner);
        else if(owner instanceof Character)
            rewardForHit((Character) owner);
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
    protected void onItemUse() {
        onMeleeUse();
    }

    @Override
    protected void onItemUpgrade() {
        onMeleeUpgrade();
    }

    @Override
    protected void onItemDowngrade() {
        onMeleeDowngrade();
    }

    protected abstract void onMeleeUse();

    protected abstract void onMeleeUpgrade();

    protected abstract void onMeleeDowngrade();

    protected abstract void rewardForKill(Character entity);

    protected abstract void rewardForHit(Character entity);
}
