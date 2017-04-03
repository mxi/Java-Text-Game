package newGame;

import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

public class MainGame {

	public static ConsoleSystemInterface csi;

    public static void main(String[] args) {
        new MainGame();
    }

    private MainGame() {
		csi = new WSwingConsoleInterface();
		csi.print(1, 1, "Why hi there! My name is 102313division by 0 error system(out) cancel~!#@!@@!");
    }

}
