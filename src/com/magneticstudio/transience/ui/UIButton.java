package com.magneticstudio.transience.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

/**
 * This class handles input via a button press.
 *
 * @author Max
 */
public class UIButton extends UIComponent {

    private UnicodeFont mainFont; // The mainFont to use for the title of the button.
    private UnicodeFont idleFont; // The mainFont to use when the button is not focused.
    private String title; // The title of this button.

    private float borderSize = 1.5f; // The size of the border.
    private boolean isHeldDown = false; // Whether this button is held down.
    private boolean isMouseHovering = false; // Whether the mouse is hovering over this button.

    /**
     * Creates a new, default, UIButton
     * object.
     */
    public UIButton() {
        title = "Button";
        mainFont = GameResources.loadFont("Ui.ttf", Color.white, 16, false, false);
        idleFont = GameResources.modifyFont(mainFont, new Color(120, 120, 120, 100), 16, false, false);
    }

    /**
     * Gets the mainFont of this button.
     * @return Font of this button.
     */
    public UnicodeFont getFont() {
        return mainFont;
    }

    /**
     * Sets the mainFont of this button.
     * @param mainFont The new mainFont for this button.
     */
    public void setFont(UnicodeFont mainFont) {
        if(mainFont == null)
            return;
        this.mainFont = mainFont;
        this.idleFont = GameResources.modifyFont(this.mainFont, new Color(120, 120, 120, 255), 0, false, false);
    }

    /**
     * Gets the title of this button.
     * @return The title of this button.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the new title for this button.
     * @param title New title for this button.
     */
    public void setTitle(String title) {
        if(title == null)
            return;
        this.title = title;
    }

    /**
     * Sends a key from a key event to this component.
     * @param type The type of keyboard event this was.
     * @param key The key pressed.
     */
    @Override
    public void interpretKey(KeyboardEventType type, Character key) {

    }

    /**
     * Sends a mouse button from a mouse event to this component.
     * @param type The type of mouse event this was.
     * @param button The button pressed.
     */
    @Override
    public void interpretMouse(MouseEventType type, Integer button) {
        isHeldDown = MouseEventType.BUTTON_PRESS == type;
    }

    /**
     * Updates this button.
     * @param parent The parent UIMenu object.
     */
    @Override
    public void update(UIMenu parent) {
        int rx = parent.getX() + getX();
        int ry = parent.getY() + getY();

        int mx = MenuMouse.getX();
        int my = MenuMouse.getY();

        isMouseHovering = mx >= rx && mx <= rx + getWidth() && my >= ry && my <= ry + getHeight();
    }

    /**
     * Renders this button onto the screen.
     * @param parent The parent UIMenu object.
     * @param graphics The graphics object used to render the component.
     */
    @Override
    public void render(UIMenu parent, Graphics graphics) {
        int rx = parent.getX() + getX();
        int ry = parent.getY() + getY();
        boolean isFocused = parent.getFocusedComponent() == this;

        Color base = new Color(255, 255, 255, 75);
        Color outline;

        if(isFocused) {
            if(isHeldDown) outline = new Color(60, 100, 120, 200);
            else if(isMouseHovering) outline = new Color(60, 100, 120, 150);
            else outline = new Color(60, 100, 120, 100);
        }
        else {
            if(isMouseHovering) outline = new Color(120, 120, 120, 175);
            else outline = new Color(120, 120, 120, 100);
        }

        graphics.setColor(outline);
        graphics.fillRect(
            rx,
            ry,
            getWidth(),
            getHeight()
        );

        graphics.setColor(base);
        graphics.fillRect(
                rx + borderSize * 2,
                ry + borderSize * 2,
                getWidth() - (borderSize * 4),
                getHeight() - (borderSize * 4)
        );

        int fx = rx + (getWidth() >> 1) - (mainFont.getWidth(title) >> 1);
        int fy = ry + (getHeight() >> 1) - (mainFont.getHeight(title) >> 1);

        if(isFocused)
            mainFont.drawString(fx, fy, title);
        else
            idleFont.drawString(fx, fy, title);
    }
}
