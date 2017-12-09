package newGame.Entities.Weapons;

import java.util.List;

import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class LongBow extends Melee {
	public LongBow() {
        setSwingRange(0);
        setName("LongBow");
        setRepresentation('B');
        setColor(ConsoleSystemInterface.TEAL);
        setDamageOutput(2);
        setRange(5f);
        setMaxDurability(10);
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());
    }
    
    @Override
    public void attackClosest(List<Entity> entities) {
        boolean attacked = false;
        for(Entity e : entities) {
            if(e.distance(getOwner()) <= getRange() && e.distance(getOwner()) > 0) {
                attack(e);
                attacked = true;
                break;
            }
        }

        if(!attacked)
            setTimesUsed(getTimesUsed() - 1);
    }
}
