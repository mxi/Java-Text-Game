package game;

public class MonstersInArea {
	/*
	Rooms----------------------
	
	RoomOne = "armory";
	RoomTwo = "mess hall";
	RoomThree = "bedroom";
	RoomFour = "prison";
	RoomFive = "kitchen";
	RoomSix = "stairwell";
	RoomSeven = "throne room";
	RoomEight = "garbage dump";
	
	ids--------------------------
	kobold => 0
	goblin => 1
	Awakened Shrub => 2
	Orc => 3
	Bullywug => 4
	
	*/
	static int[] OneSearch = {0, 1, 4, 3};
	static int[] TwoSearch = {0, 1, 3};
	static int[] ThreeSearch = {0, 1};
	static int[] FourSearch = {3};
	static int[] FiveSearch = {0, 4, 3};
	static int[] SixSearch = {0};
	static int[] SevenSearch = {0, 1, 4, 3};
	static int[] EightSearch = {2, 0, 1, 4, 4, 3};
}
