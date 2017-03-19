package game;

import java.util.Random;

public class LevelUp {
	
	static Random rand = new Random();
	
	public static void LevelUp()
	{
		character.level++;
		character.maxhp += rand.nextInt(character.hitdie) + 1;
		character.hp = character.maxhp;
		System.out.println("You leveled up to level " + character.level + "!");
		System.out.println("Your max health is now " + character.maxhp + "!");
	}
	
	public static void GoUp()
	{
		if(character.xp > 300 && character.level == 1)
		{
			LevelUp();
		}
		if(character.xp > 900 && character.level == 2)
		{
			LevelUp();
		}
		if(character.xp > 2700 && character.level == 3)
		{
			LevelUp();
		}
		if(character.xp > 6500 && character.level == 4)
		{
			LevelUp();
		}
		if(character.xp > 14000 && character.level == 5)
		{
			LevelUp();
		}
		if(character.xp > 23000 && character.level == 6)
		{
			LevelUp();
		}
		if(character.xp > 34000 && character.level == 7)
		{
			LevelUp();
		}
		if(character.xp > 48000 && character.level == 8)
		{
			LevelUp();
		}
		if(character.xp > 64000 && character.level == 9)
		{
			LevelUp();
		}
		if(character.xp > 85000 && character.level == 10)
		{
			LevelUp();
		}
		if(character.xp > 100000 && character.level == 11)
		{
			LevelUp();
		}
		if(character.xp > 120000 && character.level == 12)
		{
			LevelUp();
		}
		if(character.xp > 140000 && character.level == 13)
		{
			LevelUp();
		}
		if(character.xp > 165000 && character.level == 14)
		{
			LevelUp();
		}
		if(character.xp > 195000 && character.level == 15)
		{
			LevelUp();
		}
		if(character.xp > 225000 && character.level == 16)
		{
			LevelUp();
		}
		if(character.xp > 265000 && character.level == 17)
		{
			LevelUp();
		}
		if(character.xp > 305000 && character.level == 18)
		{
			LevelUp();
		}
		if(character.xp > 355000 && character.level == 19)
		{
			LevelUp();
		}
	}
	
}
