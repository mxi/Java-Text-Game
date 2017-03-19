package game;

public class GameOver {

	public static void GameOver() {

		if(character.hp <= 0)
		{
			System.out.println("Game Over");
			System.exit(1);
		}
	}
}
