package newGame.Entities.Weapons;

import java.util.List;

import newGame.MainGame;
import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class ShortBow extends Melee {
	public ShortBow() {
        setSwingRange(0);
        setName("Shortbow");
        setRepresentation('b');
        setColor(ConsoleSystemInterface.TEAL);
        setDamageOutput(1);
        setRange(3f);
        setMaxDurability(10);
    }

	
    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());
    }
    
    @Override
    public void attackClosest(List<Entity> entities) {
        boolean attacked = false;
        Entity closestEntity = null;
        for(Entity e : entities) {
            if(e.distance(getOwner()) <= getRange() && e.distance(getOwner()) > 0
            		&& checkWalls(MainGame.character.getX(), MainGame.character.getY(), e.getX(), e.getY(), MainGame.map.getMapBuffer())) {
            	if(closestEntity == null)
            	{
            		closestEntity = e;
            	}else if(closestEntity.distance(getOwner()) > e.distance(getOwner()))
            	{
            		closestEntity = e;
            	}
            }
        }

        attack(closestEntity);
        attacked = true;

        if(!attacked)
            setTimesUsed(getTimesUsed() - 1);
    }
    
//    @Override
//    public void attackClosest(List<Entity> entities) {
//        boolean attacked = false;
//        for(Entity e : entities) {
//            if(e.distance(getOwner()) <= getRange() && e.distance(getOwner()) > 0
//            		&& checkWalls(MainGame.character.getX(), MainGame.character.getY(), e.getX(), e.getY(), MainGame.map.getMapBuffer()))
//            {
//                attack(e);
//                attacked = true;
//                break;
//            }
//        }
//
//        if(!attacked)
//            setTimesUsed(getTimesUsed() - 1);
//    }
}
