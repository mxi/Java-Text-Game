package newGame.menus;

import newGame.MainGame;

/**
 * This component will display text onto
 * a menu screen.
 */
public class Label extends MenuComponent {

    private String text; // Text that will be displayed using this label

    /**
     * Constructor for the label class that takes in the
     * text that it will display and the location of the label.
     * @param text Text to display.
     * @param lx Location X
     * @param ly Location Y
     */
    public Label(String text, int lx, int ly) {
        super(lx, ly);
        this.text = text;
    }

    @Override
    protected void initialize() {
        if(text == null)
            text = "MenuComponent@Label";
        setFocusable(false);
    }

    @Override
    protected void destructor() {
        // Do nothing.
    }

    @Override
    protected void render(Menu menu, int color) {
        MainGame.csi.print(parent.getSceneX() + x, parent.getSceneY() + y,
                text, color);
    }

    @Override
    protected void update(Menu menu, int key) {

    }
}
