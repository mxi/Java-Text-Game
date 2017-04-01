package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import sz.csi.CharKey;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

public class MainGame {

    public static ConsoleSystemInterface csi;
    
    public static void GameOver() throws IOException
    {
    	csi.cls();
    	csi.print(1, 1, "Game Over");
    	csi.print(1, 2, "Play Again? y: yes, n: no");
    	csi.refresh();
    	for(;;)
    	{
        	int key = MainGame.csi.inkey().code;
	        switch (key){
	        case CharKey.y:
	        	Begin();
	        	break;
	        case CharKey.n:
	        	System.exit(0);
	        	break;
	        }
    	}
    }
    
	public static void Startup()
	{

		csi = new WSwingConsoleInterface();
		
		//class choosing
		
    	csi.cls();
        csi.saveBuffer();
    	csi.restore();
		for(;;)
		{
			csi.print(1, 1, "Welcome, choose your class for this adventure", ConsoleSystemInterface.WHITE);
			//class options
			csi.print(1, 2, "a: fighter");
			//end of class options
			int key = MainGame.csi.inkey().code;
			
			if(key == CharKey.a)
			{
				csi.refresh();
				character.charclass = "fighter";
				character.hp = 8;
				character.hitdie = 8;
				break;
			}
		}
		
		csi.restore();
		csi.refresh();
		
		//race choosing
		csi.print(1, 1, "Now choose your race:");
		for(;;)
		{
			//race options
			csi.print(1, 2, "a: Mountain Dwarf");
			csi.print(1, 3, "b: Hill Dwarf");
			csi.print(1, 4, "c: High Elf");
			csi.print(1, 5, "d: Wood Elf");
			csi.print(1, 6, "e: Drow");
			csi.print(1, 7, "f: Lightfoot Halfling");
			csi.print(1, 8, "g: Stout Halfling");
			csi.print(1, 9, "h: Human");
			
			csi.refresh();
			int key = MainGame.csi.inkey().code;
			
			if(key == CharKey.a)
			{
				character.race = "mountain dwarf";
				character.strength += 2;
				character.constitution += 2;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "dwarvish";
				character.langknown++;
				break;
			}else if(key == CharKey.b)
			{
				character.race = "hill dwarf";
				character.wisdom += 1;
				character.constitution += 2;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "dwarvish";
				character.langknown++;
				break;
			}else if(key == CharKey.c)
			{
				character.race = "high elf";
				character.dexterity += 2;
				character.intelligence += 1;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "elvish";
				character.langknown++;
				break;
			}else if(key == CharKey.d)
			{
				character.race = "wood elf";
				character.dexterity += 2;
				character.wisdom += 1;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "elvish";
				character.langknown++;
				break;
			}else if(key == CharKey.e)
			{
				character.race = "drow";
				character.dexterity += 2;
				character.charisma += 1;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "elvish";
				character.langknown++;
				character.language[character.langknown] = "undercommon";
				character.langknown++;
				character.language[character.langknown] = "drow";
				character.langknown++;
				break;
			}else if(key == CharKey.f)
			{
				character.race = "lightfoot halfling";
				character.dexterity += 2;
				character.charisma += 1;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "halfling";
				character.langknown++;
				break;
			}else if(key == CharKey.g)
			{
				character.race = "stout halfling";
				character.dexterity += 2;
				character.constitution += 1;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "halfling";
				character.langknown++;
				break;
			}else if(key == CharKey.h)
			{
				character.race = "human";
				character.strength += 1;
				character.dexterity += 1;
				character.intelligence += 1;
				character.wisdom += 1;
				character.charisma += 1;
				character.language[character.langknown] = "common";
				character.langknown++;
				character.language[character.langknown] = "halfling";
				character.langknown++;
				character.language[character.langknown] = "dwarvish";
				character.langknown++;
				character.language[character.langknown] = "elvish";
				character.langknown++;
				break;
			}
		}
		csi.restore();
		csi.refresh();
	}
	
<<<<<<< HEAD
	//public static void main(String[] args)throws IOException{
	//	Begin();
	//}
=======
	public static void main(String[] args){
		Begin();
	}
>>>>>>> 9225ce728e6104e0d9e01451d4420436bb4da6a5
		
	public static void Begin()
	{
		Startup(); 
		character.hp += (int) character.constitution / 2 - 5;
		character.maxhp = character.hp;
		
		csi.print(1, 1, "Here are your stats:");
		csi.print(1, 2, "Max health is " + character.maxhp);
		csi.print(1, 3, "Your armor class is " + character.AC);
		csi.print(1, 4, "Your strength is " + character.strength);
		csi.print(1, 5, "Your dexterity is " + character.dexterity);
		csi.print(1, 6, "Your constitution is " + character.constitution);
		csi.print(1, 7, "Your intelligence is " + character.intelligence);
		csi.print(1, 8, "Your wisdom is " + character.wisdom);
		csi.print(1, 9, "Your charisma is " + character.charisma);
		character.equipment[0] = "longsword";
		
		csi.print(1, 15, "One more thing, you have a limited inventory of ten items, and when\nfighting");
		csi.print(1, 16, "the null options are just empty slots for future items. m = melee, b = range");
		csi.print(1, 17, "Now you are ready for adventure! (press b to begin)");
		
		for(;;)
		{
			int key = MainGame.csi.inkey().code;
			if(key == CharKey.b)
			{
				Map.FieldAction();
			}
		}
	}
}
