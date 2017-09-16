package newGame.menus;

/**
 * A component that is put inside of a menu
 * object to perform a function when interacted
 * with on the screen.
 */
public abstract class MenuComponent {

    // Data members of the MenuComponent class.
    private Menu parent;
    private int x;
    private int y;

    private boolean isFocused = false; // Whether this component is focused or not.

    /**
     * The constructor for this MenuComponent
     * object that initializes the dimensions and
     * location of this object.
     * @param locationX Location X.
     * @param locationY Location Y.
     */
    public MenuComponent(int locationX, int locationY) {
        x = locationX;
        y = locationY;
        initialize();
    }

    /**
     * Checks if this component is focused or not.
     * @return Whether this component is focused or not.
     */
    public boolean isFocused() {
        return isFocused;
    }

    /**
     * Sets the focus state of this component.
     * @param s New focus state of this component.
     */
    public void setFocused(boolean s) {
        isFocused = s;
        parent.focusOnlyOn(this);
    }

    /**
     * Code that executes when the constructor for the MenuComponent
     * is finished, but before it returns to the caller. This will
     * be used to initialize any data members.
     */
    protected abstract void initialize();

    /**
     * Code that executes when the menu window is
     * closing/about to be closed so that this component
     * can save any data that it needs or log to
     * the logger.
     */
    protected abstract void destructor();

    /**
     * Renders this component onto the menu screen
     * (or however this component would like to
     * be rendered).
     */
    protected abstract void render();

    /**
     * Updates this component (if it's focused).
     * @param key User input key.
     */
    protected abstract void update(int key);
}
