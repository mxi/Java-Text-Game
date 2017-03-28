package game;

import java.io.IOException;

import sz.csi.CharKey;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

public class CharMove {

    public static int a = 26;
    public static int b = 2;
    public static String curMweapon = "stick";
    public static String curRweapon = "rock";

    public static int MDMax = 2;
    public static int MDMin = 1;
    public static int RDMax = 1;
    public static int RDMin = 1;
    public static int Range = 2;
    public static char prevchar = ' ';
    public static int ammunition = 0;
    public static int ammoRarity = 1;
    public static int ammoWeight = 3;
    public static char WeaponType = 'L';
    
    
    // L = light - add dex bonus - no str required
    // M = medium - add half dex bonus - 13 to 15 str required
    // H = heavy, add no dex bonus - > 15 str required
    public static int ArmorBonus = character.dexterity / 2 - 5;
    public static String Armor = "clothing";
    public static char ArmorType = 'L';

    // Shield types: None, Leather, Wood, Bone
    public static String Shield = "none";
    public static int ShieldSize = 1; // Determines whether an enemy can hit or not. (1 - 10) (size limits may be adjusted).
    public static int ShieldLevel = 0; // Level of damage absorption. (0 - 30) (level limits may be adjusted).

	/**
	 * Determines whether the character has a shield or not.
	 * @return If it has a valid shield value it returns true.
	 */
    public static boolean hasShield() {
    	return Shield.equals("none") || Shield.equals("leather") || Shield.equals("wood") || Shield.equals("bone");
    	// TODO: When more shield are added, make sure to add them here!
	}

	/**
	 * Calculates how much damage the shield absorbs.
	 * (Not based on the amount of damage being given. Rather
	 * it's based on the level of the shield and type).
	 * @return How much the shield absorbs.
	 */
    public static int calculateShieldAbsorption() {
		switch(Shield) {
			case "none": // Absorption of "none" by default is 0 @ level 0.
				return (int) Math.ceil(ShieldLevel * .05);
			case "leather": // Absorption of "leather" by default is 1 @ level 0.
				return (int) Math.ceil(ShieldLevel * .1) + 1;
			case "wood": // Absorption of "wood" by default is 2 @ level 0.
				return (int) Math.ceil(ShieldLevel * .15) + 2;
			case "bone": // Absorption of "bone" by default is 3 @ level 0.
				return (int) Math.ceil(ShieldLevel * .2) + 3;
			default: // If there is no valid shield, no damage will be absorbed.
				return 0;
		}
	}

	/**
	 * Checks whether the enemy is able to hit the character based on shield size.
	 * @return If the random value is greater than that of (level * 3) then true.
	 */
	public static boolean doesShieldHit() {
		return Map.rand.nextInt(101) >= (ShieldSize * 3);
	}

	/**
	 * Sets a shield with specified parameters:
	 * @param type Type of shield (see list above).
	 * @param size Size of the shield.
	 * @param level Level of the shield.
	 */
	public static void setShield(String type, int size, int level) {
    	Shield = type;
    	ShieldSize = size;
    	ShieldLevel = level;
	}

	public static void move(){

		//MainGame.csi.restore();
        MainGame.csi.print(a, b, "@", ConsoleSystemInterface.DARK_RED);
	    
    	int key = MainGame.csi.inkey().code;
        switch (key){
        case CharKey.l:
    		character.xp += 1000;
    		EnemyAI.LevelUp();
        	break;
        case CharKey.ENTER:
    		EnemyAI.curE = 0;
    		a = 26;
    		b = 2;
    		Map.RoomX = 25;
    		Map.RoomY = 1;
    		prevchar = ' ';
    		EnemyAI.curE = 0;
    		Map.curI = 0;
    		Map.FieldAction();
    		//MainGame.csi.saveBuffer();
        	break;
        case CharKey.m:
        	EnemyAI.EnemyInRange(1, MDMax, MDMin, character.strength / 2 - 5);
        	break;
        case CharKey.b:
        	if(ammunition > 0)
        	{
        		EnemyAI.EnemyInRange(Range, RDMax, RDMin, character.dexterity / 2 - 5);
        		ammunition--;
        	}
        	break;
        case CharKey.UARROW:
        	if(MainGame.csi.peekChar(a, b - 1) != 'X' && MainGame.csi.peekChar(a, b - 1) != 'M')
        	{
				MainGame.csi.print(a, b, Character.toString(prevchar));
        		b--;
				prevchar = MainGame.csi.peekChar(a, b);
        	}
        	break;
        case CharKey.DARROW:
        	if(MainGame.csi.peekChar(a, b + 1) != 'X' && MainGame.csi.peekChar(a, b + 1) != 'M')
        	{
				MainGame.csi.print(a, b, Character.toString(prevchar));
	        	b++;
				prevchar = MainGame.csi.peekChar(a, b);
				
        	}
        	break;
        case CharKey.LARROW:
        	if(MainGame.csi.peekChar(a - 1, b) != 'X' && MainGame.csi.peekChar(a - 1, b) != 'M')
        	{
				MainGame.csi.print(a, b, Character.toString(prevchar));
	        	a--;
				prevchar = MainGame.csi.peekChar(a, b);
				
        	}
        	break;
        case CharKey.RARROW:
        	if(MainGame.csi.peekChar(a + 1, b) != 'X' && MainGame.csi.peekChar(a + 1, b) != 'M')
        	{
				MainGame.csi.print(a, b, Character.toString(prevchar));
	        	a++;
				prevchar = MainGame.csi.peekChar(a, b);
        	}
        	break;

        }
        if(MainGame.csi.peekChar(a, b) == 'O' || MainGame.csi.peekChar(a, b) == '/')
    	{
    		EnemyAI.curE = 0;
    		Map.curI = 0;
    		a = 26;
    		b = 2;
    		Map.RoomX = 25;
    		Map.RoomY = 1;
    		prevchar = ' ';
    		EnemyAI.curE = 0;
    		Map.FieldAction();
    		//MainGame.csi.saveBuffer();
    	}else if(MainGame.csi.peekChar(a, b) == 'I')
    	{
    		main: for(int x = 0; x <= Map.curI; x++)
	    	{
    			if(Map.Items[x].x == a && Map.Items[x].y == b)
    			{
		    		Item I = Map.Items[x];
		    		if(!I.ammo && !I.IsArmor)
		    		{
		    			int Holder;
		    			String NameHolder;
			    		if(I.range == 1)
			    		{
			    			NameHolder = I.name;
			    			I.name = curMweapon;
			    			curMweapon = NameHolder;
	
			    			Holder = I.DMax;
			    			I.DMax = MDMax;
			    			MDMax = Holder;
	
			    			Holder = I.DMin;
			    			I.DMin = MDMin;
			    			MDMin = Holder;
				        	MainGame.csi.print(0, 21, "You got a " + curMweapon);
			    		}else{
			    			
			    			NameHolder = I.name;
			    			I.name = curRweapon;
			    			curRweapon = NameHolder;
	
			    			Holder = I.DMax;
			    			I.DMax = RDMax;
			    			RDMax = Holder;
	
			    			Holder = I.DMin;
			    			I.DMin = RDMin;
			    			RDMin = Holder;

			    			Holder = I.range;
			    			I.range = Range;
			    			Range = Holder;
			    			
			    			Holder = I.CarriedAmmo;
			    			I.CarriedAmmo = ammunition;
			    			ammunition = Holder;
			    			
			    			Holder = I.ammoRarity;
			    			I.ammoRarity = ammoRarity;
			    			ammoRarity = Holder;

			    			Holder = I.ammoWeight;
			    			I.ammoWeight = ammoWeight;
			    			ammoWeight = Holder;
			    			
			    			if(character.strength * 10 / ammoWeight < ammunition)
			    			{
			    				ammunition = character.strength * 10 / ammoWeight;
			    			}
			    			
				        	MainGame.csi.print(0, 21, "You got a " + curRweapon);
			    		}
			        	MainGame.csi.refresh();
			        	MainGame.csi.waitKey(1);
		    		}else if(!I.IsArmor){
		    			ammunition += I.CarriedAmmo;
		    			I.CarriedAmmo = 0;
		    			if(character.strength * 10 / ammoWeight < ammunition)
		    			{
		    				I.CarriedAmmo = ammunition - character.strength * 10 / ammoWeight;
		    				ammunition = character.strength * 10 / ammoWeight;
		    			}
		    		}else{
		    			int Holder;
		    			char CharHolder;
		    			String NameHolder;
		    			if(I.Sheild)
		    			{
			    			NameHolder = I.name;
			    			I.name = Shield;
			    			Shield = NameHolder;
	
			    			Holder = I.ArmorBonus;
			    			I.ArmorBonus = ShieldLevel;
			    			ShieldLevel = Holder;
			    			
			    			Holder = I.SheildSize;
			    			I.SheildSize = ShieldSize;
			    			ShieldSize = Holder;
			    			
				        	MainGame.csi.print(0, 21, "You got a " + Shield + " sheild");
		    			}else{
			    			NameHolder = I.name;
			    			I.name = Armor;
			    			Armor = NameHolder;
	
			    			Holder = I.ArmorBonus;
			    			I.ArmorBonus = ArmorBonus;
			    			ArmorBonus = Holder;
			    			
			    			CharHolder = I.ArmorType;
			    			I.ArmorType = ArmorType;
			    			ArmorType = CharHolder;			 

				        	MainGame.csi.print(0, 21, "You got " + Armor + " armor");
		    			}
			        	MainGame.csi.refresh();
			        	MainGame.csi.waitKey(1);
		    		}
		    		break main;
    			}
    		}
    	}
	}
}
