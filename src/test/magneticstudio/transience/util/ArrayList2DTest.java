package test.magneticstudio.transience.util;

import com.magneticstudio.transience.util.ArrayList2D;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is designed to test the class
 * "Game" in the source code package.
 *
 * @author Max
 */
public final class ArrayList2DTest {

    /**
     * Main entry point of the program.
     * @param args Arguments from command-line.
     */
    public static void main(String[] args) {
        ArrayList2D<String> array = new ArrayList2D<>(5, 5);
        array.fill("Hello");
        array.setDimensions(1, 1);
        array.printToTerminal(System.out, false);
    }
}