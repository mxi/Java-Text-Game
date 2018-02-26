package com.magneticstudio.transience.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

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

    private long lastUpdate = -1; // The last time this component has been updated.
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
        mainFont = GameResources.loadFont("Ui.ttf", Color.white, (getHeight() / 2) + 5, false, false);
        promptFont = GameResources.modifyFont(mainFont, Color.gray, (getHeight() / 2) + 5, false, false);
    }

    /**
     * Sends a key from a key event to this component.
     * @param key The key pressed.
     */
    public void interpretKey(Character key) {
        if(key == 8 && input.length() > 0)
            input.delete(input.length() - 1, input.length());
        else if(('a' <= key && key <= 'z')
                || ('A' <= key && key <= 'Z')
                || ('0' <= key && key <= '9')
                || (key == '.') || (key == ' '))
            input.append(key);
    }

    /**
     * Sends a mouse button from a mouse event to this component.
     * @param button The button pressed.
     */
    public void interpretMouse(Integer button) {
        // Do nothing...
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
        promptFont = GameResources.modifyFont(
            mainFont,
            new Color(120, 120, 120, 255),
            mainFont.getFont().getSize(),
            false,
            false
        );
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

        final Color base = new Color(255, 255, 255, 100);
        final Color outline = focused ? new Color(60, 100, 120, 125) : new Color(120, 120, 120, 125);

        graphics.setColor(outline);
        graphics.fillRect(rx, ry, getWidth(), getHeight());

        graphics.setColor(base);
        graphics.fillRect(
            rx + (CARET_X_OFFSET * 2 / 3),
            ry + (CARET_Y_OFFSET * 2 / 3),
            getWidth() - (CARET_X_OFFSET * 4 / 3),
            getHeight() - (CARET_Y_OFFSET * 4 / 3)
        );

        if(focused) {
            int caretX = mainFont.getWidth(input.toString());

            graphics.setColor(new Color(caretColor.r, caretColor.g, caretColor.b, caretOpacity));
            graphics.fillRect(
                rx + CARET_X_OFFSET + caretX,
                ry + CARET_Y_OFFSET,
                caretWidth,
                getHeight() - (CARET_Y_OFFSET * 2)
            );
            mainFont.drawString(rx + CARET_X_OFFSET, ry + (CARET_Y_OFFSET >> 1), input.toString());
        }
        else if(input.length() == 0) {
            promptFont.drawString(rx + CARET_X_OFFSET, ry + (CARET_Y_OFFSET >> 1), prompt);
        }
        else {
            promptFont.drawString(rx + CARET_X_OFFSET, ry + (CARET_Y_OFFSET >> 1), input.toString());
        }
    }
}
