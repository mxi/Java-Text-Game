package test.magneticstudio.transience.ui;

import com.magneticstudio.transience.game.InventoryStack;
import com.magneticstudio.transience.game.Item;
import com.magneticstudio.transience.ui.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.awt.*;

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
    public static void main(String[] args) throws SlickException, NoSuchFieldException, IllegalAccessException {
        Game game = new Game("Test");

        InventoryStack<Item> items = new InventoryStack<>();

        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        AppGameContainer container = new AppGameContainer(game, (int) resolution.getWidth(), (int) resolution.getHeight(), true);
        container.setIcons(new String[] {
                "resources/logos/magnetic-studio-temp-icon-16.png",
                "resources/logos/magnetic-studio-temp-icon-32.png"
        });
        container.setVerbose(false);
        container.setShowFPS(false);
        container.setVSync(true);
        container.start();
    }
}
