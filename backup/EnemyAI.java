package game;

import sz.csi.CharKey;
import sz.csi.ConsoleSystemInterface;

public class EnemyAI {

	public static Monster monsters[] = new Monster[15];

	
	public static int EHP[] = new int[15];
	public static int EYC[] = new int[15];
	public static int EXC[] = new int[15];
	public static int movesleft = 1;
	public static int curE = 0;
	public static char PreviousChars[] = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
	
	public static boolean clear(int x, int y)
	{
		if(MainGame.csi.peekChar(x, y) != 'X'
				&& MainGame.csi.peekChar(x, y) != 'O'
				&& MainGame.csi.peekChar(x, y) != '@'
				&& MainGame.csi.peekChar(x, y) != 'M'
				&& MainGame.csi.peekChar(x, y) != '/')
		{
			return true;
		}else{
			return false;
		}
	}
	
	public static void LevelUp()
	{
		LevelUp: for(;;)
		{
			if(character.xp >= character.level * character.level * 100)
			{
				character.xp = character.xp - character.level * character.level * 100;
				character.level++;
				int raise = Map.rand.nextInt(character.hitdie) + 1 + (int) character.constitution / 2 - 5;
				if(raise <= 0)
				{
					raise = 1;
				}
				character.maxhp += raise;
				character.hp = character.maxhp;
				if(character.level % 4 == 0)
				{
					for(int x = 0; x < 2; x++)
					{
						MainGame.csi.print(0, 21, "Choose two stats to raise                 ");
						MainGame.csi.print(0, 22, "S: strength, D: dexterity                 ");
						MainGame.csi.print(0, 23, "C: constitiution, I: intelligence         ");
						MainGame.csi.print(0, 24, "W: wisdom, A: charisma   ");
						MainGame.csi.refresh();
						main: for(;;)
				    	{
				        	int key = MainGame.csi.inkey().code;
					        switch (key){
					        case CharKey.s:
					        	character.strength++;
					        	break main;
					        case CharKey.d:
					        	character.dexterity++;
					        	if(CharMove.ArmorType == 'L')
					        	{
					        		CharMove.ArmorBonus += character.dexterity / 2 - 5 - ((character.dexterity - 1) / 2 - 5);
					        	}else if(CharMove.ArmorType == 'M')
					        	{
					        		CharMove.ArmorBonus += (character.dexterity / 2 - 5) / 2 - ((character.dexterity - 1) / 2 - 5) / 2;
					        	}
					        	break main;
					        case CharKey.c:
					        	character.constitution++;
					        	break main;
					        case CharKey.i:
					        	character.intelligence++;
					        	break main;
					        case CharKey.w:
					        	character.wisdom++;
					        	break main;
					        case CharKey.a:
					        	character.charisma++;
					        	break main;
					        }
				    	}
						MainGame.csi.print(0, 21, "                                          ");
						MainGame.csi.print(0, 22, "                                          ");
						MainGame.csi.print(0, 23, "                                          ");
						MainGame.csi.print(0, 24, "                                          ");
					}
				}
			}else{
				break;
			}
		}
	}
	
	public static void EnemyDied(int x)
	{
		Monster m = new Monster();
		m = monsters[x];
		//MainGame.csi.restore();
		MainGame.csi.print(m.x, m.y, Character.toString(m.prevchar), m.prevcolor);
		for(int y = x; y + 1 < curE; y++)
		{
			monsters[y] = monsters[y + 1];
		}
		curE--;
		MainGame.csi.saveBuffer();
		character.xp += m.xpWorth;
		LevelUp();
	}
	
	public static void EnemyInRange(int range, int DMax, int DMin, int modifier)
	{
		main: for(int z = range * - 1; z <= range; z++)
		{
			int damage = 0;
			if(MainGame.csi.peekChar(CharMove.a + z, CharMove.b + Math.min(range - z, range + z)) == 'M')
			{
				for(int x = 0; x < curE; x++)
				{
					Monster m = new Monster();
					m = monsters[x];
					if(m.x == CharMove.a + z && m.y == CharMove.b + Math.min(range - z, range + z))
					{
						damage = Map.rand.nextInt(DMax - DMin + 1) + DMin + modifier;
						if(damage < 1)
						{
							damage = 1;
						}
						m.hp -= damage;
						if(monsters[x].hp < 0)
						{
							monsters[x].hp = 0;
						}
						MainGame.csi.print(0, 21, "Monster took " + damage + " damage");
						MainGame.csi.print(0, 22, "Monster has " + monsters[x].hp + " health left");
						if(m.hp <= 0)
						{
							MainGame.csi.print(0, 23, "You killed it!");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
							EnemyDied(x);
						}else{
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}
						break main;
					}
				}
			}if(MainGame.csi.peekChar(CharMove.a + z, CharMove.b - Math.min(range - z, range + z)) == 'M')
			{
				for(int x = 0; x < curE; x++)
				{
					Monster m = new Monster();
					m = monsters[x];
					if(m.x == CharMove.a + z && m.y == CharMove.b - Math.min(range - z, range + z))
					{
						damage = Map.rand.nextInt(DMax - DMin + 1) + DMin + modifier;
						if(damage < 1)
						{
							damage = 1;
						}
						m.hp -= damage;
						if(monsters[x].hp < 0)
						{
							monsters[x].hp = 0;
						}
						MainGame.csi.print(0, 21, "Monster took " + damage + " damage");
						MainGame.csi.print(0, 22, "Monster has " + monsters[x].hp + " health left");
						if(m.hp <= 0)
						{
							MainGame.csi.print(0, 23, "You killed it!");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
							EnemyDied(x);
						}else{
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}
						break;
					}
				}
			}
		}
	}
	
	public static void EnemyMove()
	{
		//MainGame.csi.restore();
		if(Map.rand.nextInt(30) + 1 == 1 && curE < 14)
		{
			Monster m = new Monster();
			monsters[curE] = m;
			
			m.hp = (Map.rand.nextInt(character.level) * 3 + 1);
			m.x = Map.rand.nextInt(53) + 26;
			m.y = Map.rand.nextInt(16) + 2;
			m.prevchar = ' ';
			m.prevcolor = ConsoleSystemInterface.WHITE;
			m.TargetMode = false;
			m.DMax = character.level;
			m.DMin = character.level / 2;
			m.xpWorth = m.hp * (m.DMax + m.DMin) * 10;
			m.CurDirection = 'R';
			m.XorYfirst = 'X';
			
			for(;;)
			{
				if(MainGame.csi.peekChar(m.x, m.y) != 'X'
						&& MainGame.csi.peekChar(m.x, m.y) != 'O'
						&& MainGame.csi.peekChar(m.x, m.y) != '|'
						&& MainGame.csi.peekChar(m.x, m.y) != 'M'
						&& MainGame.csi.peekChar(m.x, m.y) != '@'
						&& MainGame.csi.peekChar(m.x, m.y) != 'I')
				{
					break;
				}
				m.x = Map.rand.nextInt(53) + 26;
				m.y = Map.rand.nextInt(16) + 2;
			}
			MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
			
			
			
			curE++;
		}
		
		for(int x = 0; x < curE; x++)
		{
			Monster m = monsters[x];
			movesleft = 1;
			
			//target mode
			
			main: if(m.TargetMode)
			{
					if(m.TargetDirection == 'U' && clear(m.x, m.y - 1))
					{
						m.TargetMode = false;
						m.XorYfirst = 'Y';
						break main;
					}else if (m.TargetDirection == 'R' && clear(m.x + 1, m.y))
					{
						m.TargetMode = false;
						m.XorYfirst = 'X';
						break main;
					}else if (m.TargetDirection == 'D' && clear(m.x, m.y + 1))
					{
						m.TargetMode = false;
						m.XorYfirst = 'Y';
						break main;
					}else if (m.TargetDirection == 'L' && clear(m.x - 1, m.y))
					{
						m.TargetMode = false;
						m.XorYfirst = 'X';
						break main;
					}
				
				if(m.CurDirection == 'U')
				{
					if(clear(m.x, m.y - 1))
					{
						char holder = MainGame.csi.peekChar(m.x, m.y - 1);
						int holderA = MainGame.csi.peekColor(m.x, m.y - 1);
						m.y--;
						MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
						MainGame.csi.print(m.x, m.y + 1, Character.toString(m.prevchar), m.prevcolor);
						m.prevchar = holder;
						m.prevcolor = holderA;
						movesleft--;
					}else{
						m.CurDirection = 'R';
						m.TargetDirection = 'U';
					}
				}
				if(m.CurDirection == 'R')
				{
					if(clear(m.x + 1, m.y))
					{
						char holder = MainGame.csi.peekChar(m.x + 1, m.y);
						int holderA = MainGame.csi.peekColor(m.x + 1, m.y);
						m.x++;
						MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
						MainGame.csi.print(m.x - 1, m.y, Character.toString(m.prevchar), m.prevcolor);
						m.prevchar = holder;
						m.prevcolor = holderA;
						movesleft--;
					}else{
						m.CurDirection = 'D';
						m.TargetDirection = 'R';
					}
				}
				if(m.CurDirection == 'D')
				{
					if(clear(m.x, m.y + 1))
					{
						char holder = MainGame.csi.peekChar(m.x, m.y + 1);
						int holderA = MainGame.csi.peekColor(m.x, m.y + 1);
						m.y++;
						MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
						MainGame.csi.print(m.x, m.y - 1, Character.toString(m.prevchar), m.prevcolor);
						m.prevchar = holder;
						m.prevcolor = holderA;
						movesleft--;
					}else{
						m.CurDirection = 'L';
						m.TargetDirection = 'D';
					}
				}
				if(m.CurDirection == 'L')
				{
					if(clear(m.x - 1, m.y))
					{
						char holder = MainGame.csi.peekChar(m.x - 1, m.y);
						int holderA = MainGame.csi.peekColor(m.x - 1, m.y);
						m.x--;
						MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
						MainGame.csi.print(m.x + 1, m.y, Character.toString(m.prevchar), m.prevcolor);
						m.prevchar = holder;
						m.prevcolor = holderA;
						movesleft--;
					}else{
						m.CurDirection = 'U';
						m.TargetDirection = 'L';
					}
				}
			}
			
			//---------------------
			if(m.XorYfirst == 'X')
			{
				if(movesleft == 1)
				{
					int damage = Map.rand.nextInt(m.DMax - m.DMin + 1) - m.DMin;
					if(damage <= 0)
					{
						damage = 1;
					}
					if(damage <= 0)
					{
						damage = 1;
					}
					if(m.x < CharMove.a)
					{
						if(MainGame.csi.peekChar(m.x + 1, m.y) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x + 1, m.y))
						{
							char holder = MainGame.csi.peekChar(m.x + 1, m.y);
							int holderA = MainGame.csi.peekColor(m.x + 1, m.y);
							m.x++;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x - 1, m.y, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'R';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'U';
							}else{
								m.CurDirection = 'D';
							}
						}
					}else if(m.x > CharMove.a)
					{
						if(MainGame.csi.peekChar(m.x - 1, m.y) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x - 1, m.y))
						{
							char holder = MainGame.csi.peekChar(m.x - 1, m.y);
							int holderA = MainGame.csi.peekColor(m.x - 1, m.y);
							m.x--;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x + 1, m.y, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'L';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'U';
							}else{
								m.CurDirection = 'D';
							}
						}
					}
				}
				if(movesleft == 1)
				{
					int damage = Map.rand.nextInt(m.DMax - m.DMin + 1) - m.DMin;
					if(damage <= 0)
					{
						damage = 1;
					}
					if(m.y < CharMove.b)
					{
						if(MainGame.csi.peekChar(m.x, m.y + 1) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x, m.y + 1))
						{
							char holder = MainGame.csi.peekChar(m.x, m.y + 1);
							int holderA = MainGame.csi.peekColor(m.x, m.y + 1);
							m.y++;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x, m.y - 1, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'D';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'R';
							}else{
								m.CurDirection = 'L';
							}
						}
					}else if(m.y > CharMove.b)
					{
						if(MainGame.csi.peekChar(m.x, m.y - 1) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x, m.y - 1))
						{
							char holder = MainGame.csi.peekChar(m.x, m.y - 1);
							int holderA = MainGame.csi.peekColor(m.x, m.y - 1);
							m.y--;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x, m.y + 1, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'U';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'R';
							}else{
								m.CurDirection = 'L';
							}
						}
					}
				}
			}else{
				
				if(movesleft == 1)
				{
					int damage = Map.rand.nextInt(m.DMax - m.DMin + 1) - m.DMin;
					if(damage <= 0)
					{
						damage = 1;
					}
					if(m.y < CharMove.b)
					{
						if(MainGame.csi.peekChar(m.x, m.y + 1) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x, m.y + 1))
						{
							char holder = MainGame.csi.peekChar(m.x, m.y + 1);
							int holderA = MainGame.csi.peekColor(m.x, m.y + 1);
							m.y++;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x, m.y - 1, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'D';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'R';
							}else{
								m.CurDirection = 'L';
							}
						}
					}else if(m.y > CharMove.b)
					{
						if(MainGame.csi.peekChar(m.x, m.y - 1) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x, m.y - 1))
						{
							char holder = MainGame.csi.peekChar(m.x, m.y - 1);
							int holderA = MainGame.csi.peekColor(m.x, m.y - 1);
							m.y--;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x, m.y + 1, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'U';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'R';
							}else{
								m.CurDirection = 'L';
							}
						}
					}
				}if(movesleft == 1)
				{
					int damage = Map.rand.nextInt(m.DMax - m.DMin + 1) - m.DMin;
					if(damage <= 0)
					{
						damage = 1;
					}
					if(m.x < CharMove.a)
					{
						if(MainGame.csi.peekChar(m.x + 1, m.y) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x + 1, m.y))
						{
							char holder = MainGame.csi.peekChar(m.x + 1, m.y);
							int holderA = MainGame.csi.peekColor(m.x + 1, m.y);
							m.x++;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x - 1, m.y, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'R';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'U';
							}else{
								m.CurDirection = 'D';
							}
						}
					}else if(m.x > CharMove.a)
					{
						if(MainGame.csi.peekChar(m.x - 1, m.y) == '@')
						{
							character.hp = character.hp - damage;
							movesleft--;
							MainGame.csi.print(0, 21, "Monster did " + damage + " damage         ");
							MainGame.csi.print(0, 22, "                                          ");
							MainGame.csi.refresh();
							MainGame.csi.waitKey(1);
						}else if(clear(m.x - 1, m.y))
						{
							char holder = MainGame.csi.peekChar(m.x - 1, m.y);
							int holderA = MainGame.csi.peekColor(m.x - 1, m.y);
							m.x--;
							MainGame.csi.print(m.x, m.y, "M", ConsoleSystemInterface.RED);
							MainGame.csi.print(m.x + 1, m.y, Character.toString(m.prevchar), m.prevcolor);
							m.prevchar = holder;
							m.prevcolor = holderA;
							movesleft--;
						}else{
							m.TargetMode = true;
							m.TargetDirection = 'L';
							if(Map.rand.nextInt(2) + 1 == 1)
							{
								m.CurDirection = 'U';
							}else{
								m.CurDirection = 'D';
							}
						}
					}
				}
			
			}
		}
		MainGame.csi.saveBuffer();
		//MainGame.csi.restore();
		
	}
}
