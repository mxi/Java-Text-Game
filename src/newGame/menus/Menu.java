package newGame.menus;

import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This menu class is the basis for all
 * Menu object that may be displayed onto
 * the screen.
 */
public class Menu {

    // Global list of all of the menus that were created.
    public static List<Menu> menus = new ArrayList<>();

    /**
     * Checks whether there is a menu that is shown
     * on the screen inside of the menus list.
     * @return Whether there is a menu that is shown.
     */
    public static boolean hasShownMenus() {
        for(Menu m : menus) {
            if(m.isShown()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the screen by re-drawing
     * the shown menus onto the screen.
     */
    public static void update() {
        for(Menu m : menus) {
            if(m.isShown()) {
                m.draw();
                break;
            }
        }
    }

    // Data members for the menu list.
    private List<MenuComponent> menuComponents = new ArrayList<>();
    private int width;
    private int height;
    private int locationX;
    private int locationY;

    private boolean isShown;

    /**
     * Constructor for the Menu class that initializes the location
     * and dimensions of this menu object.
     * @param w Width of the menu.
     * @param h Height of the menu.
     */
    public Menu(int w, int h) {
        if(w > MainGame.map.getMapWidth() || h > MainGame.map.getMapHeight()) {
            return;
        }
        width = w;
        height = h;
        locationX = (MainGame.map.getMapWidth() / 2) - (width / 2);
        locationY = (MainGame.map.getMapHeight() / 2) - (height / 2);
        menus.add(this);
    }

    /**
     * Renders the menu onto the screen.
     */
    public void draw() {
        MainGame.clearCsi(locationX, locationY, width, height);
        for(int x = 0; x < width; x++) {
            MainGame.csi.print(locationX + x, locationY, '*', ConsoleSystemInterface.GRAY);
            MainGame.csi.print(locationX + x, locationY + height - 1, '*',  ConsoleSystemInterface.GRAY);
        }
        for(int y = 1; y + 1 < height; y++) {
            MainGame.csi.print(locationX, locationY + y, '*', ConsoleSystemInterface.GRAY);
            MainGame.csi.print(locationX + width -1, locationY + y, '*', ConsoleSystemInterface.GRAY);
        }
    }

    /**
     * Checks whether this menu is shown on screen.
     * @return Whether this menu is shown or not.
     */
    public boolean isShown() {
        return isShown;
    }

    /**
     * Sets whether this menu is shown or not.
     * @param shown Shown or not status.
     */
    public void setShown(boolean shown) {
        isShown = shown;
    }

    /**
     * Hides and removes this menu object
     * from the menus list so that the
     * menu may never be displayed again
     * (unless a new object is created).
     */
    public void close() {
        isShown = false;
        menus.remove(this);
    }
}
