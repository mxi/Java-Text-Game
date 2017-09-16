package newGame.menus;

/**
 * This component will display text onto
 * a menu screen.
 */
public class Label extends MenuComponent {

    private String text; // Text that will be displayed using this label.

    /**
     * Constructor for the label class that takes in the
     * text that it will display and the location of the label.
     * @param text Text to display.
     * @param lx Location X
     * @param ly Location Y
     */
    public Label(String text, int lx, int ly) {
        super(lx, ly);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void destructor() {

    }

    @Override
    protected void render() {

    }

    @Override
    protected void update(int key) {

    }
}
