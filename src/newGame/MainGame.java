package newGame;

import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

import java.util.Random;

public class MainGame {

    public static Random random;
    public static ConsoleSystemInterface csi;

    public static void main(String[] args) {
        new MainGame();
    }

    private MainGame() {
        random = new Random();
        csi = new WSwingConsoleInterface();
    }
}
