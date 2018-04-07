package com.magneticstudio.transience.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

/**
 * This class handles input from the user via
 * the menu by typing into a buffer while displaying
 * the user's input so they may review and edit it.
 *
 * @author Max
 */
public class UITextField extends UIComponent {

    private UnicodeFont mainFont; // The font for this UITextField.
    private UnicodeFont promptFont; // The font for the prompt of this UIText field.
    private String prompt; // The text field prompt.
    private StringBuilder input; // The buffer of text input from the user.
    private Color caretColor; // The color of the caret.

    private int maximumLength = 32; // The maximum amount of characters in this text field.
    private long lastUpdate = -1; // The last time this component has been updated.
    private float borderSize = 2f / 3f; // The size of the text field border.
    private float caretWidth = 2.0f; // The width of the caret.
    private float caretOpacity = 1.0f; // The opacity of the caret.
    private float caretBlinkSpeed = 1.0f / 500.0f; // The caretBlindSpeed.

    private static final int CARET_X_OFFSET = 5; // The caret offset x.
    private static final int CARET_Y_OFFSET = 5; // The caret offset y.

    /**
     * Creates a new, default, UITextField
     * object.
     */
    public UITextField() {
        prompt = "";
        caretColor = new Color(60, 100, 120);
        input = new StringBuilder();
        mainFont = Res.loadFont("Ui.ttf", Color.white, (getHeight() / 2) + 5, Res.FALSE, Res.FALSE);
        promptFont = Res.modifyFont(mainFont, Color.gray, (getHeight() / 2) + 5, Res.FALSE, Res.FALSE);
    }

    /**
     * Sends a key from a key event to this component.
     * @param type The type of keyboard event this was.
     * @param key The key pressed.
     */
    @Override
    public void interpretKey(KeyboardEventType type, Character key) {
        if(key == 8 && input.length() > 0)
            input.delete(input.length() - 1, input.length());
        else if(('a' <= key && key <= 'z')
                || ('A' <= key && key <= 'Z')
                || ('0' <= key && key <= '9')
                || (key == '.') || (key == ' ')) {
            if(maximumLength > 0 && input.length() < maximumLength) {
                input.append(key);
            }
            else if(maximumLength < 0
                    && mainFont.getWidth(input.toString() + Character.toString(key)) < getWidth() - borderSize * 16) {
                input.append(key);
            }
        }
    }

    /**
     * Sends a mouse button from a mouse event to this component.
     * @param type The type of mouse event this was.
     * @param button The button pressed.
     */
    @Override
    public void interpretMouse(MouseEventType type, Integer button) {
        // Do nothing...
    }

    /**
     * Gets the maximum amount of characters to
     * be input into the text field.
     * @return The maximum characters in this text field.
     */
    public int getMaximumCharacters() {
        return maximumLength;
    }

    /**
     * Sets the maximum amount of characters to
     * be input into the text field. (anything
     * less than 0 will fit the input text onto
     * the text field ui bounds).
     * @param nLength The new maximum characters in this text field.
     */
    public void setMaximumLength(int nLength) {
        maximumLength = nLength;
    }

    /**
     * Gets the border size.
     * @return Border size.
     */
    public float getBorderSize() {
        return borderSize;
    }

    /**
     * Sets the size of the border.
     * @param f The new size of the border.
     */
    public void setBorderSize(float f) {
        borderSize = f;
    }

    /**
     * Gets the text field prompt.
     * @return The prompt of this text field.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Sets the text field prompt.
     * @param np The new prompt of this text field.
     */
    public void setPrompt(String np) {
        if(np == null)
            return;
        prompt = np;
    }

    /**
     * Gets the width of the caret.
     * @return Caret width.
     */
    public float getCaretWidth() {
        return caretWidth;
    }

    /**
     * Sets the caret width.
     * @param cw New caret width.
     */
    public void setCaretWidth(float cw) {
        caretWidth = Math.max(cw, 1);
    }

    /**
     * Gets the color of the caret.
     * @return Caret color.
     */
    public Color getCaretColor() {
        return caretColor;
    }

    /**
     * Sets the color of the caret/
     * @param nc New caret color.
     */
    public void setCaretColor(Color nc) {
        if(nc == null)
            return;
        caretColor = nc;
    }

    /**
     * Gets the font of this text field.
     * @return The font of this text field.
     */
    public UnicodeFont getFont() {
        return mainFont;
    }

    /**
     * Sets the font of this text field.
     * @param font The new font for this text field.
     */
    public void setFont(UnicodeFont font) {
        if(font == null)
            return;
        mainFont = font;
        promptFont = Res.modifyFont(mainFont,  new Color(120, 120, 120, 255), Res.USE_DEFAULT, Res.USE_DEFAULT, Res.USE_DEFAULT);
    }

    /**
     * Updates this component.
     * @param parent The parent UIMenu object.
     */
    @Override
    public void update(UIMenu parent) {
        if(lastUpdate == -1) {
            lastUpdate = System.currentTimeMillis();
            return;
        }

        long now = System.currentTimeMillis();
        caretOpacity -= (now - lastUpdate) * caretBlinkSpeed;
        if(caretOpacity < 0)
            caretOpacity = 1.0f;
        lastUpdate = now;
    }

    /**
     * Renders this text field onto the screen.
     * @param parent The parent UIMenu.
     * @param graphics The graphics to render this text field with.
     */
    @Override
    public void render(UIMenu parent, Graphics graphics) {
        int rx = parent.getX() + getX();
        int ry = parent.getY() + getY();
        boolean focused = parent.getFocusedComponent() == this;

        final Color base = new Color(255, 255, 255, 75);
        final Color outline = focused ? new Color(60, 100, 120, 100) : new Color(120, 120, 120, 100);

        graphics.setColor(outline);
        graphics.fillRect(rx, ry, getWidth(), getHeight());

        graphics.setColor(base);
        graphics.fillRect(
            rx + (CARET_X_OFFSET * borderSize),
            ry + (CARET_Y_OFFSET * borderSize),
            getWidth() - (CARET_X_OFFSET * borderSize * 2),
            getHeight() - (CARET_Y_OFFSET * borderSize * 2)
        );

        String displayString = input.toString();
        if(mainFont.getWidth(displayString) > getWidth() - borderSize * 8) {
            for(int i = 0; i < input.length(); i++) {
                displayString = "..." + input.substring(i, input.length());
                if(mainFont.getWidth(displayString) <= getWidth() - borderSize * 8)
                    break;
            }
        }

        if(focused) {
            int caretX = mainFont.getWidth(displayString);

            graphics.setColor(new Color(caretColor.r, caretColor.g, caretColor.b, caretOpacity));
            graphics.fillRect(
                rx + CARET_X_OFFSET + caretX,
                ry + CARET_Y_OFFSET,
                caretWidth,
                getHeight() - (CARET_Y_OFFSET * 2)
            );
            mainFont.drawString(rx + CARET_X_OFFSET, ry + (CARET_Y_OFFSET >> 1), displayString);
        }
        else if(input.length() == 0) {
            promptFont.drawString(rx + CARET_X_OFFSET, ry + (CARET_Y_OFFSET >> 1), prompt);
        }
        else {
            promptFont.drawString(rx + CARET_X_OFFSET, ry + (CARET_Y_OFFSET >> 1), displayString);
        }
    }
}
