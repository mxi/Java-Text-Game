package newGame.Entities.Weapons;

import newGame.Animations.Animations;
import newGame.Animations.SwordSwingDirection;
import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Item;
import newGame.Entities.Monsters.Monster;
import newGame.IntPoint;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.List;

public abstract class Melee extends Item {

    private static final double singleRange = Math.sqrt(2);

    private int damageOutput;
    private float swingRange;
    private float range;

    public Melee() {
        setRepresentation('M');
        setColor(ConsoleSystemInterface.MAGENTA);
        setName("Melee");
        damageOutput = 1;
        swingRange = 0;
        range = (float) Math.sqrt(2);
    }

    public float getSwingRange() {
        return swingRange;
    }

    public void setSwingRange(float swingRange) {
        this.swingRange = swingRange;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float r) {
        range = r;
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
        Entity owner = getOwner();
        if(owner != null) {
            Animations.swordSwing(SwordSwingDirection.getFromPositionOffset(owner, entity),
                        owner.getX(), owner.getY());
        }
        onAttack(entity);
        if(entity.isDead() && owner != null && owner instanceof Character) {
            ((Character)owner).addExp(((Monster)entity).randExp());
        }
    }

    public void attackClosest(List<Entity> entities) {
        boolean attacked = false;
        boolean isMonster = getOwner() instanceof Monster;
        for(Entity e : entities) {
            if(e == getOwner() || (e instanceof Monster && isMonster))
                continue;

            if(e.distance(getOwner()) <= singleRange) {
                attack(e);
                attacked = true;
                break;
            }
        }

        if(!attacked)
            setTimesUsed(getTimesUsed() - 1);
    }

    public void swing(List<Entity> entities) {
        if(!isSwingWeapon() || entities.size() == 0) {
            setTimesUsed(getTimesUsed() - 1);
            return;
        }

        boolean isMonster = getOwner() instanceof Monster;
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if(e == getOwner() || (e instanceof  Monster && isMonster))
                continue;

            IntPoint ownerPos = getOwner().getPosition();
            if(e.distance(ownerPos.getX(), ownerPos.getY()) <= swingRange) {
                attack(e);
            }
        }
    }

    @Override
    protected void onItemUse() {
        Entity owner = getOwner();
        if(owner == null) {
            return;
        }
        if(isSwingWeapon()) {
            swing(MainGame.map.getEntities());
        }
        else {
            attackClosest(MainGame.map.getEntities());
        }
    }

    protected abstract void onAttack(Entity entity);
}
