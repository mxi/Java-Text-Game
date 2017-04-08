package newGame;

import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

public class MainGame {

    public static ConsoleSystemInterface csi;

    public static void main(String[] args) {
    	for(;;)
    	{
    		new MainGame();
    	}
    }

    private MainGame() {
        csi = new WSwingConsoleInterface();
        csi.print(1, 1, "Reverted back to CSI");
        int input = csi.inkey().code;
        csi.print(1, 2, "You pressed " + input);
        csi.refresh();
        csi.waitKey(10);
    }

}
