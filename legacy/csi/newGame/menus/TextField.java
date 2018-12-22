package newGame.menus;

import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

/**
 * This object will be used to get text input from
 * the user using the csi instead of another
 * api such as swing.
 */
public class TextField extends MenuComponent {

    private StringBuilder text; // String buffer to contain the text.
    private int tflength; // The length of this text field.
    private int maxTextLength; // The maximum text length of the text field.

    /**
     * Main constructor for this text field.
     * @param lx Location X.
     * @param ly Location Y.
     * @param tl TextField length.
     */
    public TextField(int lx, int ly, int tl) {
        super(lx, ly);
        tflength = tl;
        maxTextLength = 64;
    }

    /**
     * Sets the text of this textfield object.
     * @param text New text of this textfield object.
     */
    public void setText(String text) {
        this.text.delete(0, this.text.length());
        this.text.append(text);
    }

    /**
     * Gets the text that is contained within the StringBuilder object.
     * @return Text contained within the string builder object.
     */
    public String getText() {
        return text.toString();
    }

    /**
     * Sets the maximum length of text that this texfield
     * can store.
     * @param ntl The new maximum length of text this textfield can store.
     */
    public void setMaxTextLength(int ntl) {
        if(ntl < 0)
            return;
        maxTextLength = ntl;
    }

    /**
     * Gets the maximum length of text that this textfield
     * can store.
     * @return Maximum number of characters this textfield can store.
     */
    public int getMaxTextLength() {
        return maxTextLength;
    }

    @Override
    protected void initialize() {
        text = new StringBuilder();
    }

    @Override
    protected void destructor() {
        // Do nothing.
    }

    @Override
    protected void render(Menu menu, int color) {
        StringBuilder tfbar = new StringBuilder(); // TextField bar that indicates that this is a text field.
        for(int i = 0; i < tflength; i++)
            tfbar.append('-');
        String disptext; // The text that will actually be displayed.
        int typeIndex; // Indicator for the user to tell which character they're on.
        if(text.length() > tflength) {
            disptext = text.substring(text.length() - tflength, text.length());
            typeIndex = tflength;
        }
        else {
            disptext = text.toString();
            typeIndex = disptext.length();
        }
        MainGame.csi.print(parent.getSceneX() + x, parent.getSceneY() + y, disptext, color);
        MainGame.csi.print(parent.getSceneX() + x, parent.getSceneY() + y + 1, tfbar.toString(), color);
        if(isFocused()) {
            MainGame.csi.print(parent.getSceneX() + x + typeIndex, parent.getSceneY() + y + 1, '_', color);
        }
    }

    @Override
    protected void update(Menu menu, int key) {
        switch(key) {
            case 116: // Do nothing
            case 10: // Enter key
            case 1:
                break;
            case 127: // LControl
                text.delete(0, text.length());
            case 11: // Delete key
                if(text.length() <= 0)
                    return;
                text.deleteCharAt(text.length() - 1);
                break;
            case 40: // Space character
                if(text.length() < maxTextLength)
                    text.append(' ');
                break;
            default:
                if(text.length() < maxTextLength)
                    text.append((char) (key + 1));
                break;
        }
    }
}
