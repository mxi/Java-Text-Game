package newGame.menus;

/**
 * This is a simple menu that will have
 * a label to display a message.
 */
public class NotificationMenu extends Menu {

    protected static int MAX_TEXT_WIDTH_SPAN = 24; // Maximum width of each line of text.
    protected static int MAX_TEXT_HEIGHT_SPAN = 6;  // Maximum height of the entire text.

    private NotificationText text; // The text that will be displayed on the label. (array for each line)

    /**
     * Creates a new NotificationMenu object that will be a child of
     * another menu object.
     * @param text Text to display on this menu.
     * @return NotificationMenu object created for another menu object.
     */
    public static NotificationMenu createForExistingMenu(NotificationText text) {
        return new NotificationMenu(text, true);
    }

    /**
     * Creates a new NotificationMenu object that will be a standalone
     * menu object containing some labels displaying text.
     * @param text Text to display on the menu.
     * @return NotificationMenu object created as a standalone menu object.
     */
    public static NotificationMenu createStandaloneMenu(NotificationText text) {
        return new NotificationMenu(text, false);
    }

    /**
     * Constructor for the NotificationMenu class that takes in
     * the message string to be displayed onto the screen.
     * @param notificationText Text to be displayed on the menu box.
     */
    private NotificationMenu(NotificationText notificationText, boolean isChild) {
        super(notificationText.getProcessedTextWidth() + 2, notificationText.getProcessedTextHeight() + 2, isChild);
        text = notificationText;
    }
}
