package newGame.menus;

import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

/**
 * This object will be used as a button. When
 * a user presses enter (number 10), it will be
 * pressed.
 */
public class Button extends MenuComponent {

    private Runnable onAction; // The events that will run once this button is pressed.
    private String title; // Title of this button.

    /**
     * Main constructor for the Button object.
     * @param title Title of the button.
     * @param bx X position of the button.
     * @param by Y position of the button.
     */
    public Button(String title, int bx, int by) {
        super(bx, by);
        this.title = title;
    }

    /**=
     * @return Gets the onAction runnable.
     */
    public Runnable getOnAction() {
        return onAction;
    }

    /**
     * @param onAction New action that will be performed on press.
     */
    public void setOnAction(Runnable onAction) {
        this.onAction = onAction;
    }

    @Override
    protected void initialize() {
        if(title == null)
            title = "MenuComponent@Button";
    }

    @Override
    protected void destructor() {

    }

    @Override
    protected void render(Menu menu, int color) {
        char outline = (char) -1;

        for(int ix = x; ix <= x + title.length() + 1; ix++) {
            MainGame.csi.print(parent.getSceneX() + ix, parent.getSceneY() + y, outline, color);
            MainGame.csi.print(parent.getSceneX() + ix, parent.getSceneY() + y + 2, outline, color);
        }
        MainGame.csi.print(parent.getSceneX() + x, parent.getSceneY() + y + 1, outline, color);
        MainGame.csi.print(parent.getSceneX() + x + title.length() + 1, parent.getSceneY() + y + 1, outline, color);
        MainGame.csi.print(parent.getSceneX() + x + 1, parent.getSceneY() + y + 1, title, color);
    }

    @Override
    protected void update(Menu menu, int key) {
        switch (key) {
            case 10: // enter
                if(onAction != null)
                    onAction.run();
                render(menu, ConsoleSystemInterface.GRAY);
                try { Thread.sleep(290); }
                catch (InterruptedException e) { /* Do nothing */ }
                render(menu, ConsoleSystemInterface.YELLOW);
                break;
            default:
                break;
        }
    }
}
