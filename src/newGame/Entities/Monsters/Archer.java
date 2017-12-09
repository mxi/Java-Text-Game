package newGame.Entities.Monsters;

import newGame.MainGame;
import newGame.Entities.Character;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.ShortBow;
import newGame.Mapping.Tile;
import sz.csi.ConsoleSystemInterface;

public class Archer extends Monster{
	public static final String NAME = "Archer";
    public static final int SPAWN_CHANCE = 3;
    public static final int LIMIT = 6;

    public Archer() {
        setColor(ConsoleSystemInterface.BLUE);
        setName(NAME);
        setRepresentation('A');

        ShortBow ArcherBow = new ShortBow();
        ArcherBow.setMaxDurability(999);
        ArcherBow.setDamageOutput(2);
        setMeleeWeapon(ArcherBow);
        setShield(Shield.Leather);
        setExpRewardMax(20);
        setExpRewardMin(5);
    }

    @Override
    public void performAI(Character character) {
        if(distance(character) <= getMeleeWeapon().getRange()) {
            getMeleeWeapon().useItem();
        }
        if(!inSight(character))
            findAI(character);
        else
            chaseAI(character);
    }
    
    @Override
    protected void chaseAI(Character c) {
        int deltaX = getX() - c.getX();
        int deltaY = getY() - c.getY();

        int absX = Math.abs(deltaX);
        int absY = Math.abs(deltaY);

        if(!(MainGame.character.getX() < getX() + .5
				&& MainGame.character.getX() > getX() - .5)
        		|| !(MainGame.character.getY() < getY() + .5
				&& MainGame.character.getY() > getY() - .5))
        {
	        if(absX > absY) {
	            if (!MainGame.map.getTile(previewMove(deltaX > 0 ? -1 : 1, 0)).isSolid())
	                move(deltaX > 0 ? -1 : 1, 0);
	            else
	                move(0, deltaY > 0 ? -1 : 1);
	        } else if(absY > absX) {
	            if(!MainGame.map.getTile(previewMove(0, deltaY > 0 ? -1 : 1)).similar(Tile.WALL))
	                move(0, deltaY > 0 ? -1 : 1);
	            else
	                move(deltaX > 0 ? -1 : 1, 0);
	        }
        }
    }
    
    /*   HALT TACTIC
    @Override
    protected void chaseAI(Character c) {
        int deltaX = getX() - c.getX();
        int deltaY = getY() - c.getY();

        int absX = Math.abs(deltaX);
        int absY = Math.abs(deltaY);

        if(!(MainGame.character.getX() < getX() + 1
				|| MainGame.character.getX() > getX() - 1
				|| MainGame.character.getY() < getY() + 1
				|| MainGame.character.getY() > getY() - 1))
        {
	        if(absX > absY) {
	            if (!MainGame.map.getTile(previewMove(deltaX > 0 ? -1 : 1, 0)).isSolid())
	                move(deltaX > 0 ? -1 : 1, 0);
	            else
	                move(0, deltaY > 0 ? -1 : 1);
	        } else if(absY > absX) {
	            if(!MainGame.map.getTile(previewMove(0, deltaY > 0 ? -1 : 1)).similar(Tile.WALL))
	                move(0, deltaY > 0 ? -1 : 1);
	            else
	                move(deltaX > 0 ? -1 : 1, 0);
	        }
        }
    }
    */
}
