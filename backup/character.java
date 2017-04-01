package game;

import java.util.Random;

public class character {

	static Random rand = new Random();
	
	static String playername = "";
	
	static int level = 1;
	static int xp = 0;
	static String charclass = "";
	static String race = "";
	
	static int speed = 30;
	static int initiative = 0;
	
	static int hp;
	static int maxhp;
	static int hitdie;
	static int AC = 10;
	
	static int strength = (int) rand.nextInt(16) + 3;
	static int dexterity = (int) rand.nextInt(16) + 3;
	static int constitution = (int) rand.nextInt(16) + 3;
	static int intelligence = (int) rand.nextInt(16) + 3;
	static int wisdom = (int) rand.nextInt(16) + 3;
	static int charisma = (int) rand.nextInt(16) + 3;
	
	static String[] equipment = new String[9];
	static String[] language = new String[999];
	static int invent = 1;
	static int langknown = 0;
}
