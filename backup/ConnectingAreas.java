package game;

public class ConnectingAreas {
	
	public static int[] R1C = {1, 2, 3, 6, 7};
	public static int[] R2C = {1, 3, 4, 6};
	public static int[] R3C = {1, 2, 3, 5, 6};
	public static int[] R4C = {2, 4, 5};
	public static int[] R5C = {2, 4, 8};
	public static int[] R6C = {1, 2, 3};
	public static int[] R7C = {1, 8};
	public static int[] R8C = {5, 7};
	
	public static int[] match(int x)
	{
		if(x == 1)
		{
			return R1C;
		}else if(x == 2)
		{
			return R2C;
		}else if(x == 3)
		{
			return R3C;
		}else if(x == 4)
		{
			return R4C;
		}else if(x == 5)
		{
			return R5C;
		}else if(x == 6)
		{
			return R6C;
		}else if(x == 7)
		{
			return R7C;
		}else if(x == 8)
		{
			return R8C;
		}else{
			return R1C;
		}
	}
}
