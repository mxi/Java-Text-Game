package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Item;

import java.awt.*;
import java.util.List;

public abstract class Melee extends Item {

    private int damageOutput;
    private int swingRange;

    private int expRewardForKill;
    private int expRewardForHit;

    public Melee() {
        setName("Melee");
        swingRange = 0;

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

    public int getSwingRange() {
        return swingRange;
    }

    public void setSwingRange(int swingRange) {
        this.swingRange = swingRange;
    }

    public boolean isSwingWeapon() {
        return swingRange > 0;
    }

    public int getDamageOutput() {
        return damageOutput;
    }

    public void setDamageOutput(int damageOutput) {
        this.damageOutput = damageOutput;
    }

    public void attack(Entity entity) {
        onAttack(entity);

        if(entity.isDead() && getOwner() instanceof Character)
            rewardForKill((Character) getOwner());
        else if(getOwner() instanceof Character)
            rewardForHit((Character) getOwner());
    }

    public void swing(List<Entity> entities) {
        if(!isSwingWeapon())
            return;

        entities.forEach(entity -> {
            Point ownerPosition = getOwner().getPosition();
            if(entity.distance(ownerPosition.getX(), ownerPosition.getY()) <= swingRange)
                attack(entity);
        });
    }

    @Override
    protected void onItemUse() {
        // Nothing...
    }

    protected abstract void onAttack(Entity entity);

    protected abstract void rewardForKill(Character entity);

    protected abstract void rewardForHit(Character entity);
}
