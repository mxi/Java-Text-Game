package newGame.menus;

/**
 * A component that is put inside of a menu
 * object to perform a function when interacted
 * with on the screen.
 */
public abstract class MenuComponent {

    // Data members of the MenuComponent class.
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * The constructor for this MenuComponent
     * object that initializes the dimensions and
     * location of this object.
     * @param locationX Location X.
     * @param locationY Location Y.
     * @param w Width of this component.
     * @param h Heigh of this component.
     */
    public MenuComponent(int locationX, int locationY, int w, int h) {
        x = locationX;
        y = locationY;
        width = w;
        height = h;
        initialize();
    }

    /**
     * Code that executes when the constructor for the MenuComponent
     * is finished, but before it returns to the caller. This will
     * be used to initialize any data members.
     */
    protected abstract void initialize();

}
