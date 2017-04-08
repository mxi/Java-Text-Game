package newGame;

import java.util.List;
import java.util.ArrayList;

public class Map {
	
	private final int DUNGEON_LEFT_MAX = 10;
	private final int DUNGEON_TOP = 3;
	private final int DUNGEON_RIGHT_MAX = 79;
	private final int DUNGEON_BOTTOM = 18;
	
	public static class Room
	{
		public int Xsize;
		public int Ysize;
		public int X;
		public int Y;
		// the x and y cords are found in the top left corner
	}
	
	public static class Hallway
	{
		public int length;
	}
	
	public static class Tile
	{
		public char TileType; //X: wall, _: empty, |: door, $: treasure, P: player, M: monster
		public char TileVisibility; //V: visible, P: seen before, but out of sight, I: haven't seen before
		public int TileX;
		public int TileY;
	}
	
	private Map()
	{
		List<Room> rooms = new ArrayList<>();
		List<Hallway> hallways = new ArrayList<>();
		List<Tile> Tiles = new ArrayList<>();
		for(Boolean ReachedBorder = false; !ReachedBorder ;)
		{
			
		}
	}
}
