package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import sz.csi.ConsoleSystemInterface;

/*

	text: 18 - 23
	
	
	Dungeon sizes:
	
	Right max point: 79
	Left max point: 25
	Bottom max point: 18
	Top max point: 1
	Spawn corner: 26, 2 
	
 */

public class Map {
	
	static Item Items[] = new Item[15];
	static int curI = 0;
	
	static String input;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int SC = 0;
	
	static Random rand = new Random();
	
	
	static int RoomX = 25;
	static int RoomY = 1;
	static int entrance = 5;
	
	
	static int RoomID = rand.nextInt(6) + 1;
	static String CurRoom;
	static int CurFloor = 1;
	
	static String RoomOne = "armory";
	static String RoomTwo = "mess hall";
	static String RoomThree = "bedroom";
	static String RoomFour = "prison";
	static String RoomFive = "kitchen";
	static String RoomSix = "stairwell";
	static String RoomSeven = "throne room";
	static String RoomEight = "garbage dump";
	
	public static String RoomIdentify(int x)
	{
		if(x == 1)
		{
			return RoomOne;
		}else if(x == 2)
		{
			return RoomTwo;
		}else if(x == 3)
		{
			return RoomThree;
		}else if(x == 4)
		{
			return RoomFour;
		}else if(x == 5)
		{
			return RoomFive;
		}else if(x == 6)
		{
			return RoomSix;
		}else if(x == 7)
		{
			return RoomSeven;
		}else if(x == 8)
		{
			return RoomEight;
		}else{
			return RoomOne;
		}
	}
	
	public static int[] RoomMonsters(int x)
	{
		if(x == 1)
		{
			return MonstersInArea.OneSearch;
		}else if(x == 2)
		{
			return MonstersInArea.TwoSearch;
		}else if(x == 3)
		{
			return MonstersInArea.ThreeSearch;
		}else if(x == 4)
		{
			return MonstersInArea.FourSearch;
		}else if(x == 5)
		{
			return MonstersInArea.FiveSearch;
		}else if(x == 6)
		{
			return MonstersInArea.SixSearch;
		}else if(x == 7)
		{
			return MonstersInArea.SevenSearch;
		}else if(x == 8)
		{
			return MonstersInArea.EightSearch;
		}else{
			return MonstersInArea.OneSearch;
		}
	}

	public static void RoomBuild()
	{
		int leaveX = 25;
		int leaveY = 1;
		for(; leaveX < 79; leaveX++)
		{
			if(MainGame.csi.peekChar(leaveX, leaveY) != 'X')
			{
				MainGame.csi.print(leaveX, leaveY, "/");
			}
		}
		for(; leaveY < 18; leaveY++)
		{
			if(MainGame.csi.peekChar(leaveX, leaveY) != 'X')
			{
				MainGame.csi.print(leaveX, leaveY, "/");
			}
		}
		leaveX = 25;
		for(; leaveX < 79; leaveX++)
		{
			if(MainGame.csi.peekChar(leaveX, leaveY) != 'X')
			{
				MainGame.csi.print(leaveX, leaveY, "/");
			}
		}
		leaveX = 25;
		leaveY = 1;
		for(; leaveY < 18; leaveY++)
		{
			if(MainGame.csi.peekChar(leaveX, leaveY) != 'X')
			{
				MainGame.csi.print(leaveX, leaveY, "/");
			}
		}
		
		int AvailableDoors = 4;
		int DoorPositions[] = {1, 1, 1, 1};
		int size = rand.nextInt(4) + 5;
		int doors;
		
		/*
			north: 1
			west: 0
			south: 2
			east: 3
			
			
			door locations
			east/west  y = RoomY + 2
			north/south x = RoomX + 2
		 */
		if(RoomX + size <= 79 && RoomY + size <= 18)
		{
			int x = RoomX;
			int y = RoomY;
			
			for(int countdown = size; countdown > 0; countdown--)
			{
				MainGame.csi.print(x, y, "X");
				x++;
			}
			x = RoomX;
			y = RoomY;
			for(int countdown = size; countdown > 0; countdown--)
			{
				MainGame.csi.print(x, y, "X");
				y++;
			}for(int countdown = size; countdown > 0; countdown--)
			{
				MainGame.csi.print(x, y, "X");
				x++;
			}
			y = RoomY;
			for(int countdown = size; countdown >= 0; countdown--)
			{
				MainGame.csi.print(x, y, "X");
				y++;
			}
			
			
			if(RoomX <= 31)
			{
				AvailableDoors--;
				DoorPositions[0] = 0;
			}else if(RoomX == 79)
			{
				AvailableDoors--;
				DoorPositions[3] = 0;
			}
			if(RoomY <= 6)
			{
				AvailableDoors--;
				DoorPositions[1] = 0;
			}else if(RoomY == 18)
			{
				AvailableDoors--;
				DoorPositions[2] = 0;
			}
			doors = rand.nextInt(AvailableDoors - 1) + 2;
			for(int z = 0; doors > 0; z++)
			{
				if(DoorPositions[z] == 1)
				{
					doors--;
					if(z == 1)
					{
						MainGame.csi.print(RoomX + 3, RoomY, "|");
					}else if(z == 3)
					{
						MainGame.csi.print(RoomX + size, RoomY + 3, "|");
						int ORoomX = RoomX;
						int ORoomY = RoomY;
						RoomX += size + 1;
						entrance = 3;
						RoomBuild();
						RoomX = ORoomX;
						RoomY = ORoomY;
						
					}else if(z == 2)
					{
						MainGame.csi.print(RoomX + 3, RoomY + size, "|");
						int ORoomX = RoomX;
						int ORoomY = RoomY;
						RoomY += size + 1;
						entrance = 2;
						RoomBuild();
						RoomX = ORoomX;
						RoomY = ORoomY;
					}else if(z == 0)
					{
						MainGame.csi.print(RoomX, RoomY + 3, "|");
					}
				}
			}
			int stair = rand.nextInt(2) + 1;
			if(stair == 1)
			{
				MainGame.csi.print(RoomX + 2,RoomY + 2, "O");
			}
			for(int w = 0; w < 10; w++)
			{
				if(curI < 14)
				{
					int Item = rand.nextInt(4) + 1;
					if(Item == 1)
					{
						Item I = new Item();
						Items[curI] = I;
	
						I.x = Map.rand.nextInt(53) + 26;
						I.y = Map.rand.nextInt(16) + 2;
						if(rand.nextInt(1) + 1 <= 1)
						{
							I.ammo = false;
							int ItemChoose = rand.nextInt(character.level * 5) + 1;
							int Choose;
							if(ItemChoose < 10 && ItemChoose > 0)
							{
								Choose = rand.nextInt(4) + 1;
								if(Choose == 1)
								{
									I.name = "club";
									I.DMax = 4;
									I.DMin = 1;
									I.range = 1;
									I.CarriedAmmo = 0;
								}else if(Choose == 2){
									I.name = "slingshot";
									I.DMax = 2;
									I.DMin = 1;
									I.range = 2;
									I.CarriedAmmo = 20;
									I.ammoRarity = 1;
									I.ammoWeight = 3;
								}else if(Choose == 3){
									I.name = "leather";
									I.ArmorBonus = 1 + character.dexterity / 2 - 5;
									I.ArmorType = 'L';
									I.Sheild = false;
									I.IsArmor = true;
								}else{
									I.name = "leather";
									I.ArmorBonus = 1;
									I.Sheild = true;
									I.IsArmor = true;
								}
							}else if(ItemChoose < 14 && ItemChoose >= 10)
							{
								I.name = "old knife";
								I.DMax = 4;
								I.DMin = 2;
								I.range = 1;
								I.CarriedAmmo = 0;
							}else if(/*ItemChoose < 20 && */ItemChoose >= 14)
							{
								if(rand.nextInt(2) + 1 == 1)
								{
									I.name = "old sword";
									I.DMax = 5;
									I.DMin = 1;
									I.range = 1;
									I.CarriedAmmo = 0;
								}else{
									I.name = "old bow";
									I.DMax = 4;
									I.DMin = 2;
									I.range = 2;
									I.CarriedAmmo = 10;
									I.ammoRarity = 5;
									I.ammoWeight = 5;
								}
							}
						}else{
							I.ammo = true;
							I.CarriedAmmo = rand.nextInt(20) + 11 - CharMove.ammoRarity;
						}
						
						for(;;)
						{
							if(MainGame.csi.peekChar(I.x, I.y) != 'X'
									&& MainGame.csi.peekChar(I.x, I.y) != 'O'
									&& MainGame.csi.peekChar(I.x, I.y) != '|'
									&& MainGame.csi.peekChar(I.x, I.y) != 'M'
									&& MainGame.csi.peekChar(I.x, I.y) != '@'
									&& MainGame.csi.peekChar(I.x, I.y) != 'I')
							{
								break;
							}
							I.x = Map.rand.nextInt(53) + 26;
							I.y = Map.rand.nextInt(16) + 2;
						}
						MainGame.csi.print(I.x, I.y, "I", ConsoleSystemInterface.BLUE);
						curI++;
					}
				}
			}
		}else{
			if(entrance == 3)
			{
				MainGame.csi.print(RoomX - 1, RoomY + 3, "X");
			}else if(entrance == 2)
			{
				MainGame.csi.print(RoomX + 3, RoomY - 1, "X");
			}
				
		}
		
		MainGame.csi.refresh();
	}
	
	public static void MapAction() throws IOException
	{
		for(;;)
		{
			
			System.out.println("You are in a(n) " + CurRoom + ", what do you do?");
			System.out.println("1: Search");
			System.out.println("2: Review Stats");
			System.out.println("3: Leave\n");
			input = br.readLine();
			if(input.charAt(0) == '1')
			{
				SC++;
				if(RoomID == 1)
				{
					int result = rand.nextInt(4);
					if(result == 0)
					{
						System.out.print("You found a dagger!");
						PickUp.PickUp("dagger");
					}else if(result == 1)
					{
						System.out.print("You found a scimitar!");
						PickUp.PickUp("scimitar");
					}else if(result == 2)
					{
						System.out.print("You found a greataxe!");
						PickUp.PickUp("greataxe");
					}else if(result == 3)
					{
						Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
					}
				}
				
				else if(RoomID == 2)
				{
					Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
				}
				
				else if(RoomID == 3)
				{
					System.out.print("You found a dagger!");
					PickUp.PickUp("dagger");
					System.out.println("Do you want to sleep in the bed?");
					System.out.println("1: Yes");
					System.out.println("2: No");
					for(;;)
					{
						input = br.readLine();
						if(input.charAt(0) == '1')
						{
							int result = rand.nextInt(2);
							if(result == 1)
							{
								System.out.println("You had a very relaxing rest");
								character.hp = character.maxhp;
							}else{
								Battle.enemyroll = 20;
								Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
							}
						}else if(input.charAt(0) != '0')
						{
							System.out.println("That is not an option");
						}
					}
				}
				
				else if(RoomID == 4)
				{
					if(rand.nextInt(6) == 0)
					{
						Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
					}
					if(SC == 3)
					{
						System.out.println("You barely notice a small vial hidden in the corner of the room\nwhen you pick it up, it bursts open in a bright green flash");
						character.xp += 500;
						LevelUp.LevelUp();
					}else{
						System.out.println("You find nothing but dust");
					}
				}
				
				else if(RoomID == 5)
				{
					System.out.println("You find a dagger!");
					PickUp.PickUp("dagger");
					System.out.println("But then...");
					Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
				}
				
				else if(RoomID == 6)
				{
					for(;;)
					{
						System.out.println("There is a staircase, do you want to go down?\n1: yes\n2:no");
						input = br.readLine();
						if(input.charAt(0) == '1')
						{
							CurFloor++;
							System.out.println("You are now on floor " + CurFloor);
							break;
						}else if(input.charAt(0) != '2')
						{
							System.out.println("That is not an option");
						}
					}
					RoomID = rand.nextInt(6) + 1;
					CurRoom = RoomIdentify(RoomID);
				}
				
				else if(RoomID == 7)
				{
					System.out.println("There is an abundance of gold!");
					character.xp += 3000;
					LevelUp.LevelUp();
					System.out.println("But then...");
					Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
				}
				
				else if(RoomID == 8)
				{
					System.out.println("You find nothing but garbage");
					Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
				}
			}else if(input.charAt(0) == '2')
			{
				System.out.println("Max health is " + character.maxhp);
				System.out.println("Your armor class is " + character.AC);
				System.out.println("Your strength is " + character.strength);
				System.out.println("Your dexterity is " + character.dexterity);
				System.out.println("Your constitution is " + character.constitution);
				System.out.println("Your intelligence is " + character.intelligence);
				System.out.println("Your wisdom is " + character.wisdom);
				System.out.println("Your charisma is " + character.charisma);
				System.out.println("\n\n");
			}else if(input.charAt(0) == '3')
			{
				if(rand.nextInt(2) == 0)
				{
					Battle.fight(RoomMonsters(RoomID)[rand.nextInt(CurFloor)]);
				}
				int[] AreaConnection = ConnectingAreas.match(RoomID);
				RoomID = AreaConnection[rand.nextInt(AreaConnection.length)];
				CurRoom = RoomIdentify(RoomID);
				SC = 0;
			}
		}
	}

	public static void FieldAction() throws IOException
	{
		MainGame.csi.cls();
		MainGame.csi.print(0, 2, "Class: " + character.charclass);
		MainGame.csi.print(0, 3, "Race: " + character.race);
		RoomBuild();
		MainGame.csi.saveBuffer();
		for(;;)
		{
			MainGame.csi.saveBuffer();
			MainGame.csi.print(0, 1, "Level: " + character.level);
			MainGame.csi.print(0, 4, character.hp + "/" + character.maxhp + " hp  ");
			MainGame.csi.print(0, 5, "Str: " + character.strength);
			MainGame.csi.print(0, 6, "Dex: " + character.dexterity);
			MainGame.csi.print(0, 7, "Con: " + character.constitution);
			MainGame.csi.print(0, 8, "Int: " + character.intelligence);
			MainGame.csi.print(0, 9, "Wis: " + character.wisdom);
			MainGame.csi.print(0, 10, "Cha: " + character.charisma);
			MainGame.csi.print(0, 11, character.xp + " xp out of " + (character.level * character.level * 100));
			MainGame.csi.print(0, 13, "Armor: " + CharMove.Armor + "|Type: " + CharMove.ArmorType + "");
			MainGame.csi.print(0, 14, "Sheild: " + CharMove.Shield + "|bonus: " + CharMove.ShieldSize + "");
			MainGame.csi.print(0, 15, "ArmorBonus: " + CharMove.ArmorBonus + "");
			MainGame.csi.print(0, 16, "Melee weapon:" + CharMove.curMweapon);
			MainGame.csi.print(0, 17, "Range weapon:" + CharMove.curRweapon);
			MainGame.csi.print(0, 18, "Ammo:" + CharMove.ammunition + " out of " + (character.strength * 10 / CharMove.ammoWeight) + "  ");
			if(CharMove.WeaponType == 'L')
			{
				MainGame.csi.print(0, 19, "Sheild is used");
			}else{
				MainGame.csi.print(0, 19, "Sheild is not used");
			}
			MainGame.csi.print(0, 21, "                                          ");
			MainGame.csi.print(0, 22, "                                          ");
			MainGame.csi.print(0, 23, "                                          ");
			MainGame.csi.print(0, 24, "                                          ");
			MainGame.csi.refresh();
			CharMove.move();
			EnemyAI.EnemyMove();
			MainGame.csi.print(0, 13, "                         ");
			MainGame.csi.print(0, 14, "                         ");
			MainGame.csi.print(0, 15, "                         ");
			MainGame.csi.print(0, 16, "                         ");
			MainGame.csi.print(0, 17, "                         ");
			/*
			if(character.hp < 1)
			{
				MainGame.GameOver();
			}
			*/
			//MainGame.csi.restore();
		}
	}
}
