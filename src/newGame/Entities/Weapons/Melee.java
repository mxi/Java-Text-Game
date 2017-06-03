package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Item;
import newGame.IntPoint;

import java.util.ArrayList;
import java.util.List;

public abstract class Melee extends Item {

    private int damageOutput;
    private int swingRange;

    private int expRewardForKill;
    private int expRewardForHit;

    public Melee() {
        setName("Melee");
        damageOutput = 1;
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

        if(entity.isDead() && getOwner() instanceof Character) {
            rewardForKill((Character) getOwner());
            entity.removeAndClean();
        } else if(getOwner() instanceof Character) {
            rewardForHit((Character) getOwner());
        }
    }

    public void swing(List<Entity> entities) {
        if(!isSwingWeapon() || entities.size() == 0) {
            setTimesUsed(getTimesUsed() - 1);
            return;
        }

        for(Entity e : entities) {
            IntPoint ownerPosition = getOwner().getPosition();
            if(e.distance(ownerPosition.getX(), ownerPosition.getY()) <= swingRange)
                attack(e);
        }
    }

    @Override
    protected void onItemUse() {
        List<Entity> otherEntities = new ArrayList<>(); // Entities excluding the player.
        Character character = null;
        for(Entity e : Entity.entities) {
            if(e instanceof Character)
                character = (Character) e;
            else
                otherEntities.add(e);
        }

        if(isSwingWeapon()) {
            swing(otherEntities);
        }
        else if(otherEntities.size() > 0) {

        }

    }

    protected abstract void onAttack(Entity entity);

    protected abstract void rewardForKill(Character entity);

    protected abstract void rewardForHit(Character entity);
}
