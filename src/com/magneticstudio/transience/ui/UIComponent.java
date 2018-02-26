package com.magneticstudio.transience.ui;

import org.newdawn.slick.Graphics;

/**
 * This class provides basic functions and data members
 * required by each UIComponent object to be a part of
 * a menu screen.
 *
 * @author Max
 */
public abstract class UIComponent {

    private int x = 0; // The X value of the position of this UIComponent.
    private int y = 0; // The Y value of the position of this UIComponent.
    private int width = 120; // The width of this UIComponent.
    private int height = 120; // The height of this UIComponent.

    /**
     * Creates a new UIComponent object
     * with default values for each data member.
     */
    public UIComponent() {

    }

    /**
     * Creates an new UIComponent object.
     * @param x The X value of the position of this component.
     * @param y The Y value of the position of this component.
     */
    public UIComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates an new UIComponent object.
     * @param x The X value of the position of this component.
     * @param y The Y value of the position of this component.
     * @param width The width of this component.
     * @param height The height of this component.
     */
    public UIComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the X value of the location of this component.
     * @return The X value of the location of this component.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X value of the location of this component.
     * @param x New X value of the location of this component.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the Y value of the location of this component.
     * @return The Y value of the location of this component.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y value of the location of this component.
     * @param y New Y value of the location of this component.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the position of this component.
     * @param x New X value of the location of this component.
     * @param y New Y value of the location of this component.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the width of this component.
     * @return Width of this component.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of this component.
     * @param width New width of this component.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of this component.
     * @return Height of this component.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of this component.
     * @param height New height of this component.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the dimensions of this component.
     * @param width New width of this component.
     * @param height New height of this component.
     */
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sends a key from a key event to this component.
     * @param key The key pressed.
     */
    public abstract void interpretKey(Character key);

    /**
     * Sends a mouse button from a mouse event to this component.
     * @param button The button pressed.
     */
    public abstract void interpretMouse(Integer button);

    /**
     * Updates this component.
     * @param parent The parent UIMenu object.
     */
    public abstract void update(UIMenu parent);

    /**
     * Renders this UIComponent object
     * onto the menu screen.
     * @param parent The parent UIMenu object.
     * @param graphics The graphics object used to render the component.
     */
    public abstract void render(UIMenu parent, Graphics graphics);
}
