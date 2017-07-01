package newGame.Mapping;

import sz.csi.ConsoleSystemInterface;

public class MapBuffer {

    private char[][] charBuffer;
    private int[][] colorBuffer;
    private int width;
    private int height;
    public MapBuffer(int width, int height) {
        charBuffer = new char[width][height];
        colorBuffer = new int[width][height];
        this.width = width;
        this.height = height;
    }
    
    public void set(char c, int color, int x, int y) {
        charBuffer[x][y] = c;
        colorBuffer[x][y] = color;
    }

    public void set(char c, int x, int y) {
        set(c, ConsoleSystemInterface.WHITE, x, y);
    }

    public char getChar(int x, int y) {
        return charBuffer[x][y];
    }

    public int getColor(int x, int y) {
        return colorBuffer[x][y];
    }

    public void print(ConsoleSystemInterface csi) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                csi.print((1 + x), (1 + y), getChar(x, y), getColor(x, y));
            }
        }
    }

    public void printAndRefresh(ConsoleSystemInterface csi) {
        print(csi);
        csi.refresh();
    }
}
