package newGame.LevelUp;

import newGame.Entities.*;
import newGame.Entities.Character;

public class LevelUp {
	
	public boolean canLevelUp(Character c)
	{
		return (c.getExp() > c.getExpUntilLevelUp());
	}
	
	public int newExpUntilLevelUp(Character c)
	{
		return (int) (Math.pow(1.05, c.getLevel()) * 100);
	}
	
	public void levelUp(Character c)
	{
		for(; c.getExp() >= c.getExpUntilLevelUp();)
		{
			c.setLevel(c.getLevel() + 1);
			c.setExpUntilLevelUp(newExpUntilLevelUp(c));
			c.setMaxHealth(c.getMaxHealth() + 5);
		}
	}
}
