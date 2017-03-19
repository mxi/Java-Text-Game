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
    public static int ammunition = 20;
    public static int ammoRarity = 1;
    public static int ammoWeight = 3;
    public static String Armor = "clothing";
    public static char ArmorType = 'L';
    // L = light - add dex bonus - no str required
    // M = medium - add half dex bonus - 13 to 15 str required
    // H = heavy, add no dex bonus - > 15 str required
    public static int ArmorBonus = character.dexterity / 2 - 5;
    
	public static void move() throws IOException{

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
		    		if(!I.ammo)
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
				        	MainGame.csi.print(0, 20, "You got a " + curMweapon);
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
			    			
				        	MainGame.csi.print(0, 20, "You got a " + curRweapon);
			    		}
			        	MainGame.csi.refresh();
			        	MainGame.csi.waitKey(1);
		    		}else{
		    			ammunition += I.CarriedAmmo;
		    			I.CarriedAmmo = 0;
		    			if(character.strength * 10 / ammoWeight < ammunition)
		    			{
		    				I.CarriedAmmo = ammunition - character.strength * 10 / ammoWeight;
		    				ammunition = character.strength * 10 / ammoWeight;
		    			}
		    		}
		    		break main;
    			}
    		}
    	}
	}
}
