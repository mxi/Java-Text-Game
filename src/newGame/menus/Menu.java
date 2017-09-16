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
     * Returns the first menu in the list
     * that is shown on screen.
     * @return
     */
    public static Menu getFirstShown() {
        for(Menu m : menus) {
            if(m.isShown()) {
                return m;
            }
        }
        return null;
    }

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
     * Updates a menu window using a specified
     * key press from the user of the game.
     * @param key Key that the user pressed (-1 for refresh).
     */
    public static void updateShown(int key) {
        for(int i = 0; i < menus.size(); i++) {
            Menu m = menus.get(i);
            if(m.isShown() && !m.isAwaitingDestruction()) {
                m.update(key);
                break;
            }
        }
    }

    // Data members for the menu list.
    private Runnable onClose; // Actions that will occur before this menu objects closes.
    private List<MenuComponent> menuComponents = new ArrayList<>();
    private int focusedComponent = -1;
    private String title;

    private int width;
    private int height;
    private int locationX;
    private int locationY;

    private boolean awaitingDestruction; // Whether this window is ready to be closed.
    private boolean isShown;

    /**
     * Constructor for the Menu class that initializes the location
     * and dimensions of this menu object.
     * @param w Width of the menu.
     * @param h Height of the menu.
     */
    public Menu(int w, int h) {
        if(w > MainGame.map.getMapWidth() || h > MainGame.map.getMapHeight()) {
            System.err.println("[WARNING] Menu window out of bounds: Screen=" + MainGame.map.getMapWidth()
                + ", " + MainGame.map.getMapHeight() + " | Menu=" + w + ", " + h);
            return;
        }
        title = "MWin";
        width = w < 6 ? 6 : w;
        height = h < 6 ? 6 : h;
        locationX = (MainGame.map.getMapWidth() / 2) - (width / 2);
        locationY = (MainGame.map.getMapHeight() / 2) - (height / 2);
        menus.add(this);
    }

    /**
     * Gets the X position of this menu object.
     * @return X value of the position of the menu object.
     */
    public int getLocationX() {
        return locationX;
    }

    /**
     * Gets the Y position of this menu object.
     * @return Y vvalue of the position of the menu object.
     */
    public int getLocationY() {
        return locationY;
    }

    /**
     * Gets the left most corner of this menu scene X.
     * @return Scene X
     */
    public int getSceneX() {
        return locationX + 1;
    }

    /**
     * Gets the top most corner of this menu scene Y.
     * @return Scene Y
     */
    public int getSceneY() {
        return locationY + 3;
    }

    /**
     * Renders the menu onto the screen.
     */
    public void draw() {
        MainGame.clearCsi(locationX, locationY, width, height);
        for(int x = 0; x < width; x++) {
            MainGame.csi.print(locationX + x, locationY, (char) -1, ConsoleSystemInterface.GRAY);
            MainGame.csi.print(locationX + x, locationY + 2, (char) -1, ConsoleSystemInterface.GRAY);
            MainGame.csi.print(locationX + x, locationY + height - 1, (char) -1,  ConsoleSystemInterface.GRAY);
            MainGame.csi.print(locationX + 1, locationY + 1, title, ConsoleSystemInterface.WHITE);
        }
        for(int y = 1; y + 1 < height; y++) {
            MainGame.csi.print(locationX, locationY + y, (char) -1, ConsoleSystemInterface.GRAY);
            MainGame.csi.print(locationX + width -1, locationY + y, (char) -1, ConsoleSystemInterface.GRAY);
        }
        for(MenuComponent m : menuComponents)
            if(m != null)
                m.render(m.isFocused() ? ConsoleSystemInterface.YELLOW : ConsoleSystemInterface.WHITE);
        MainGame.csi.refresh();
    }

    /**
     * Updates the menu window and uses the
     * int key value to base the updates on.
     */
    public void update(int key) {
        if(awaitingDestruction)
            return;

        draw();
        switch(key) {
            case 30: // escape; exit menu window
                close();
                return;
            case 2: // left arrow key
                focusPreviousComponent();
                return;
            case 3: // right arrow key
                focusNextComponent();
                return;
            default: // Do nothing.
                break;
        }
        MenuComponent focused = getFocusedComponent();
        if(focused != null && key > 0) {
            focused.update(key);
        }
    }

    /**
     * Gets the state of the menu on whether it's ready to be
     * destroyed or not.
     * @return Whether this menu is ready to be destroyed or not.
     */
    private boolean isAwaitingDestruction() {
        return awaitingDestruction;
    }

    /**
     * Gets the focused component of this Menu
     * screen.
     * @return Focused Menu Component object.
     */
    private MenuComponent getFocusedComponent() {
        if(menuComponents.size() == 0)
            return null;
        else
            return menuComponents.get(focusedComponent);
    }

    /**
     * Adds a component to the GUI list.
     * @param component Component to add.
     */
    public void addComponent(MenuComponent component) {
        menuComponents.add(component);
        component.parent = this;
        component.initialize();
        if(focusedComponent == -1) {
            component.setFocused(true);
            focusedComponent = 0;
        }
    }

    /**
     * Gets the title of this menu window.
     * @return Title of menu window.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this menu window and
     * makes sure it will fit within the width boundary
     * of this menu window.
     * @param t New title of this menu window.
     */
    public void setTitle(String t) {
        if(t.length() > width - 2) {
            title = t.substring(0, width - 5) + "...";
        }
        else {
            title = t;
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
     * Sets the actions that will occur once this
     * menu object closes.
     * @param onClose Actions that will run on menu close.
     */
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    /**
     * Hides and removes this menu object
     * from the menus list so that the
     * menu may never be displayed again
     * (unless a new object is created).
     */
    public void close() {
        awaitingDestruction = true;
        if(onClose != null) {
            onClose.run();
        }
        menuComponents.forEach(MenuComponent::destructor);
        menus.remove(this);
    }

    /**
     * Unfocuses the current component and focuses
     * the next component in the menu components list.
     * If it has looped over all components, the counter
     * will wrap around to the first element in the
     * menuComponents list.
     */
    public void focusNextComponent() {
        if(menuComponents.size() <= 1) {
            return;
        }
        menuComponents.get(focusedComponent).setFocused(false);
        focusedComponent++;
        if(focusedComponent >= menuComponents.size())
            focusedComponent = 0;
        menuComponents.get(focusedComponent).setFocused(true);
    }

    /**
     * Unfocuses the current component
     * and focuses the previous component in the menu
     * components list.
     * If it has looped backwards over all of the items in the
     * list it will wrap back to the end of the list and
     * decrement from there over the components again.
     */
    public void focusPreviousComponent() {
        if(menuComponents.size() <= 1) {
            return;
        }
        menuComponents.get(focusedComponent).setFocused(false);
        focusedComponent--;
        if(focusedComponent < 0)
            focusedComponent = menuComponents.size() - 1;
        menuComponents.get(focusedComponent).setFocused(true);
    }
}
