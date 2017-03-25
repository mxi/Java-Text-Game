package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Battle {
	
	static Random rand = new Random();
	
	static int enemyroll;
	static int playerroll;
	
	static boolean persuade;
	
	static class enemy{
		static String name;
		
		static int speed;
		static int initiative;
		
		static int hp;
		static int AC;
		
		static int strength;
		static int dexterity;
		static int constitution;
		static int intelligence;
		static int wisdom;
		static int charisma;
		
		static String[] equipment;
		static String[] language;
		static int xpworth;
		static boolean flee = false;
	}
	//--------------------------------------------------------------------------------
	
	public static boolean LangMatch()
	{
		for(int x = 0; x < character.language.length; x++)
		{
			for(int y = 0; y < enemy.language.length; y++)
			{
				if(character.language[x].equalsIgnoreCase(enemy.language[y]))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	//--------------------------------------------------------------------------------
	
	public static void enemyattack()
	{
		if(enemy.name.equals("Kobold"))
		{
			EAI.KoboldAI();
		}else if(enemy.name.equals("Goblin"))
		{
			EAI.GoblinAI();
		}else if(enemy.name.equals("Awakened Shrub"))
		{
			EAI.AwakenedShrubAI();
		}else if(enemy.name.equals("Orc"))
		{
			EAI.OrcAI();
		}else if(enemy.name.equals("Bullywug"))
		{
			EAI.BullywugAI();
		}
	}
	
	//--------------------------------------------------------------------------------
	
	public static void playerattack() throws IOException
	{
		System.out.println("\n\nWhat do you do?");
		String input;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(;;)
		{
			System.out.println("\nf: flee");
			System.out.println("p: persuade");
			System.out.println("0: fist");
			for(int x = 0; x < character.equipment.length; x++)
			{
				System.out.println((x + 1) + ": " + character.equipment[x]);
			}
			input = br.readLine();
			
			if(input.charAt(0) == 'f' || input.charAt(0) == 'F')
			{
					enemy.hp = 0;
					enemy.flee = true;
			}else if(input.charAt(0) == 'p' || input.charAt(0) == 'P')
			{
				if(LangMatch())
				{
					if(rand.nextInt(20) + 1 + ((int) character.charisma / 2 - 5) > rand.nextInt(20) + 1 + ((int) enemy.wisdom / 2 - 5))
					{
						enemy.hp = 0;
						persuade = true;
					}else{
						System.out.println("Your attempt failed");
					}
				}else{
					System.out.println("You don't speak the same language!");
				}
			}
			else if((int) input.charAt(0) == 48){
				
				if((int) rand.nextInt(20) + 1 + ((int) character.strength / 2 - 5) > enemy.AC)
				{
					int damage = 1 + (int) character.strength / 2 - 5;
					if(damage < 1)
					{
						damage = 1;
					}
					enemy.hp -= damage;
					System.out.println("You hit and did " + damage + " damage!");
				}else{
					System.out.println("You missed");
				}
				
			}else if(character.equipment[((int) input.charAt(0)) - 49].equals("longsword")){
				
				if((int) rand.nextInt(20) + 1 + ((int) character.strength / 2 - 5) > enemy.AC)
				{
					int damage = (int) rand.nextInt(8) + 1 + (int) character.strength / 2 - 5;
					if(damage < 1)
					{
						damage = 1;
					}
					enemy.hp -= damage;
					System.out.println("You hit and did " + damage + " damage!");
				}else{
					System.out.println("You missed");
				}
				
			}else if(character.equipment[((int) input.charAt(0)) - 49].equals("dagger")){
				
				if((int) rand.nextInt(20) + 1 + (Math.max( (int) character.strength / 2 - 5, (int) character.dexterity / 2 - 5) )> enemy.AC)
				{
					int damage = (int) rand.nextInt(4) + 1 + (Math.max( (int) character.strength / 2 - 5, (int) character.dexterity / 2 - 5) );
					if(damage < 1)
					{
						damage = 1;
					}
					enemy.hp -= damage;
					System.out.println("You hit and did " + damage + " damage!");
				}else{
					System.out.println("You missed");
				}
			}else if(character.equipment[((int) input.charAt(0)) - 49].equals("scimitar")){
				
				if((int) rand.nextInt(20) + 1 + (Math.max( (int) character.strength / 2 - 5, (int) character.dexterity / 2 - 5) )> enemy.AC)
				{
					int damage = (int) rand.nextInt(6) + 1 + (Math.max( (int) character.strength / 2 - 5, (int) character.dexterity / 2 - 5) );
					if(damage < 1)
					{
						damage = 1;
					}
					enemy.hp -= damage;
					System.out.println("You hit and did " + damage + " damage!");
				}else{
					System.out.println("You missed");
				}
			}else if(character.equipment[((int) input.charAt(0)) - 49].equals("greataxe")){
				
				if((int) rand.nextInt(20) + 1 + (int) character.strength / 2 - 5 > enemy.AC)
				{
					int damage = (int) rand.nextInt(12) + 1 + (int) character.strength / 2 - 5;
					if(damage < 1)
					{
						damage = 1;
					}
					enemy.hp -= damage;
					System.out.println("You hit and did " + damage + " damage!");
				}else{
					System.out.println("You missed");
				}
			}else if(character.equipment[((int) input.charAt(0)) - 49].equals("spear")){
				
				if((int) rand.nextInt(20) + 1 + (int) character.strength / 2 - 5 > enemy.AC)
				{
					int damage = (int) rand.nextInt(6) + 1 + (int) character.strength / 2 - 5;
					if(damage < 1)
					{
						damage = 1;
					}
					enemy.hp -= damage;
					System.out.println("You hit and did " + damage + " damage!");
				}else{
					System.out.println("You missed");
				}
			}else{
				System.out.println("that is not an option");
				continue;
			}
			break;
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------
	
	public static void statmatch(int id)
	{
		if(id == Kobold.id)
		{
			
			enemy.hp = Kobold.hp;
			enemy.name = Kobold.name;
			enemy.speed = Kobold.speed;
			enemy.initiative = Kobold.initiative;
			enemy.AC = Kobold.AC;
			enemy.strength = Kobold.strength;
			enemy.dexterity = Kobold.dexterity;
			enemy.constitution = Kobold.constitution;
			enemy.intelligence = Kobold.intelligence;
			enemy.wisdom = Kobold.wisdom;
			enemy.charisma = Kobold.charisma;
			enemy.equipment = Kobold.equipment;
			enemy.xpworth = Kobold.xpworth;
			enemy.language = Kobold.language;
			
		}else if(id == Goblin.id){
			
			enemy.hp = Goblin.hp;
			enemy.name = Goblin.name;
			enemy.speed = Goblin.speed;
			enemy.initiative = Goblin.initiative;
			enemy.AC = Goblin.AC;
			enemy.strength = Goblin.strength;
			enemy.dexterity = Goblin.dexterity;
			enemy.constitution = Goblin.constitution;
			enemy.intelligence = Goblin.intelligence;
			enemy.wisdom = Goblin.wisdom;
			enemy.charisma = Goblin.charisma;
			enemy.equipment = Goblin.equipment;
			enemy.xpworth = Goblin.xpworth;
			enemy.language = Goblin.language;
			
		}else if(id == AwakenedShrub.id){
			
			enemy.hp = AwakenedShrub.hp;
			enemy.name = AwakenedShrub.name;
			enemy.speed = AwakenedShrub.speed;
			enemy.initiative = AwakenedShrub.initiative;
			enemy.AC = AwakenedShrub.AC;
			enemy.strength = AwakenedShrub.strength;
			enemy.dexterity = AwakenedShrub.dexterity;
			enemy.constitution = AwakenedShrub.constitution;
			enemy.intelligence = AwakenedShrub.intelligence;
			enemy.wisdom = AwakenedShrub.wisdom;
			enemy.charisma = AwakenedShrub.charisma;
			enemy.equipment = AwakenedShrub.equipment;
			enemy.xpworth = AwakenedShrub.xpworth;
			enemy.language = AwakenedShrub.language;
			
		}else if(id == Orc.id){
			
			enemy.hp = Orc.hp;
			enemy.name = Orc.name;
			enemy.speed = Orc.speed;
			enemy.initiative = Orc.initiative;
			enemy.AC = Orc.AC;
			enemy.strength = Orc.strength;
			enemy.dexterity = Orc.dexterity;
			enemy.constitution = Orc.constitution;
			enemy.intelligence = Orc.intelligence;
			enemy.wisdom = Orc.wisdom;
			enemy.charisma = Orc.charisma;
			enemy.equipment = Orc.equipment;
			enemy.xpworth = Orc.xpworth;
			enemy.language = Orc.language;
			
		}else if(id == Bullywug.id){
			
			enemy.hp = Bullywug.hp;
			enemy.name = Bullywug.name;
			enemy.speed = Bullywug.speed;
			enemy.initiative = Bullywug.initiative;
			enemy.AC = Bullywug.AC;
			enemy.strength = Bullywug.strength;
			enemy.dexterity = Bullywug.dexterity;
			enemy.constitution = Bullywug.constitution;
			enemy.intelligence = Bullywug.intelligence;
			enemy.wisdom = Bullywug.wisdom;
			enemy.charisma = Bullywug.charisma;
			enemy.equipment = Bullywug.equipment;
			enemy.xpworth = Bullywug.xpworth;
			enemy.language = Bullywug.language;
			
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------
	
	public static void fight(int id) throws IOException
	{
		statmatch(id);
		System.out.println("You are attacked by a(n) " + enemy.name + "!");
		for(;enemy.hp > 0 && character.hp > 0;)
		{
			System.out.println("\n\n You have " + character.hp + " health");
			if(enemyroll != 20)
			{
				enemyroll = (int) (rand.nextInt(20) + 1) + enemy.initiative;
				playerroll = (int) (rand.nextInt(20) + 1) + character.initiative;
				if(enemyroll > playerroll)
				{
					System.out.println("The " + enemy.name + " goes first!");
					enemyattack();
					if(character.hp <= 0)
					{
						break;
					}
					playerattack();
				}else{
					System.out.println("You go first!");
					playerattack();
					if(enemy.hp <= 0)
					{
						break;
					}
					enemyattack();
				}
			}else{
				System.out.println("You are surprise attacked ny a(n) " + enemy.name + "!");
				enemyattack();
				GameOver.GameOver();
			}
			enemyroll = 0;
			playerroll = 0;
		}
		GameOver.GameOver();
		if(enemy.flee)
		{
			System.out.println("You escaped");
			
			int[] AreaConnection = ConnectingAreas.match(Map.RoomID);
			Map.RoomID = AreaConnection[rand.nextInt(AreaConnection.length)];
			Map.CurRoom = Map.RoomIdentify(Map.RoomID);
			Map.SC = 0;
		}else if(persuade)
		{
			System.out.println("The enemy left, and for your persuasion you got " + (enemy.xpworth / (2 * character.level)) + " experience!");
			character.xp += enemy.xpworth / (2 * character.level);
			LevelUp.GoUp();
			
		}else{
			System.out.println("You killed the " + enemy.name + "! You got " + enemy.xpworth + " experience");
			character.xp += enemy.xpworth;
			LevelUp.GoUp();
			
			if(enemy.equipment.length > 0)
			{
				String item = enemy.equipment[rand.nextInt(enemy.equipment.length)];
				System.out.print("You also recieved a(n) " + item + " from the " + enemy.name);
				PickUp.PickUp(item);
			}
		}
		
		System.out.println("\n\n");
	}
}
