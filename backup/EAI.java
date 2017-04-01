package game;

import java.util.Random;

public class EAI {
	
	static Random rand = new Random();
	
	public static void KoboldAI()
	{
		int attack = 0;
		System.out.println("\n\nThe kobold uses its dagger!");
		attack = (int) rand.nextInt(20) + 1 + ((int) Kobold.dexterity / 2 - 5);
		if(attack > character.AC)
		{
			attack = (int) rand.nextInt(4) + 1 + ((int) Kobold.dexterity / 2 - 5);
			System.out.println("The kobold hit and did " + attack + " damage");
			character.hp -= attack;
		}else{
			System.out.println("The kobold missed");
		}
	}
	public static void GoblinAI()
	{
		int attack = 0;
		System.out.println("\n\nThe goblin uses its scimitar!");
		attack = (int) rand.nextInt(20) + 1 + ((int) Goblin.dexterity / 2 - 5);
		if(attack > character.AC)
		{
			attack = (int) rand.nextInt(6) + 1 + ((int) Goblin.dexterity / 2 - 5);
			System.out.println("The goblin hit and did " + attack + " damage");
			character.hp -= attack;
		}else{
			System.out.println("The goblin missed");
		}
	}
	public static void AwakenedShrubAI()
	{
		int attack = 0;
		System.out.println("\n\nThe shrub swings its branches at you!");
		attack = (int) rand.nextInt(20) + 1 + ((int) AwakenedShrub.dexterity / 2 - 5);
		if(attack > character.AC)
		{
			attack = (int) rand.nextInt(4) + 1 + ((int) AwakenedShrub.dexterity / 2 - 5);
			System.out.println("The shrub hit and did " + attack + " damage");
			character.hp -= attack;
		}else{
			System.out.println("The shrub missed");
		}
	}
	public static void OrcAI()
	{
		int attack = 0;
		System.out.println("\n\nThe orc uses its greataxe!");
		attack = (int) rand.nextInt(20) + 1 + ((int) Orc.strength / 2 - 5);
		if(attack > character.AC)
		{
			attack = (int) rand.nextInt(12) + 1 + ((int) Orc.strength / 2 - 5);
			System.out.println("The orc hit and did " + attack + " damage");
			character.hp -= attack;
		}else{
			System.out.println("The orc missed");
		}
	}
	public static void BullywugAI()
	{
		int attack = 0;
		System.out.println("\n\nThe Bullywug uses its spear!");
		attack = (int) rand.nextInt(6) + 1 + ((int) Bullywug.strength / 2 - 5);
		if(attack > character.AC)
		{
			attack = (int) rand.nextInt(6) + 1 + ((int) Bullywug.strength / 2 - 5);
			System.out.println("The Bullywug hit and did " + attack + " damage");
			character.hp -= attack;
		}else{
			System.out.println("The Bullywug missed");
		}
		
		System.out.println("Then it bites you!");
		attack = (int) rand.nextInt(4) + 1 + ((int) Bullywug.strength / 2 - 5);
		if(attack > character.AC)
		{
			attack = (int) rand.nextInt(4) + 1 + ((int) Bullywug.strength / 2 - 5);
			System.out.println("The Bullywug hit and did " + attack + " damage");
			character.hp -= attack;
		}else{
			System.out.println("The Bullywug missed");
		}
	}
	
}
