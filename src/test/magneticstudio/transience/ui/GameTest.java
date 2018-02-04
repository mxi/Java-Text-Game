package test.magneticstudio.transience.ui;

import com.magneticstudio.transience.ui.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * This class is designed to test the class
 * "Game" in the source code package.
 *
 * @author Max
 */
public final class GameTest {

    /**
     * Main entry point of the program.
     * @param args Arguments from command-line.
     */
    public static void main(String[] args) throws SlickException {
        Game game = new Game("Test");
        game.addKeyTracker(Input.KEY_SPACE, () -> System.out.println("Pressed space bar."));

        AppGameContainer container = new AppGameContainer(game, 1280, 720, false);
        container.setIcons(new String[] {
                "resources/logos/magnetic-studio-temp-icon-16.png",
                "resources/logos/magnetic-studio-temp-icon-32.png"
        });
        container.setShowFPS(true);
        container.setVSync(true);
        container.start();
    }
}
