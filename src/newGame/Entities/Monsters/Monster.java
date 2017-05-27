package newGame.Entities.Monsters;

import game.MainGame;
import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Melee;

public abstract class Monster extends Entity {

    private Shield shield;
    private Melee meleeWeapon;
    private boolean FindMode;
    private int Persistance;
    private int FindCount;

    public Monster() {

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
        // TODO: Work on range weapons some time
        return false;
    }

    public boolean hasMeleeWeapon() {
        return meleeWeapon != null;
    }

    @Override
    public void onEntityUpgrade() {
        onMonsterUpgrade();
    }

    protected abstract void onMonsterUpgrade();

    public abstract void performAI(Character character);
    
    public boolean PlayerInSight(Character character, Goblin goblin)
    {
    	for(int x = goblin.getX();; x++)
    	{
    		if(MainGame.csi.peekChar(x, goblin.getY()) == '@')
    		{
    	    	return true;
    		}else if(MainGame.csi.peekChar(x, goblin.getY()) == 'X')
    		{
    	    	break;
    		}
    	}
    	for(int y = goblin.getY();; y++)
    	{
    		if(MainGame.csi.peekChar(goblin.getX(), y) == '@')
    		{
    	    	return true;
    		}else if(MainGame.csi.peekChar(goblin.getX(), y) == 'X')
    		{
    	    	return false;
    		}
    	}
    }
    
	public void FindAI(Character character, Goblin goblin, char direction /*Direction trying to go*/, int Persistance) {
		if(PlayerInSight(character, goblin))
    	{
    		FindMode = false;
    		performAI(character);
    	}else{
    		if(direction == 'L')
    		{
    			// go up
    			goblin.move(0, -1);
    		}else if(direction == 'R')
    		{
    			// go down
    			goblin.move(0, 1);
    		}else if(direction == 'U')
    		{
    			// go right
    			goblin.move(-1, 0);
    		}else if(direction == 'D')
    		{
    			// go left
    			goblin.move(1, 0);
    		}
    	}if(FindCount == Persistance)
		{
    		FindMode = false;
		}
	}
}
