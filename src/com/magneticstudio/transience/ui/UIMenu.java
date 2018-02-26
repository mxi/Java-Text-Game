package com.magneticstudio.transience.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that displays a set of components
 * for the user to interact with in the game.
 *
 * @author Max
 */
public abstract class UIMenu implements MenuKeyboardInterceptor, MenuMouseInterceptor {

    protected List<UIComponent> componentList = new ArrayList<>(); // List of components on this menu object.
    protected int focusedComponent = -1; // The index of the focused component in this menu.

    private int x; // The x location of this menu.
    private int y; // The y location of this menu.
    private int width; // The width of this menu.
    private int height; // The height of this menu.

    /**
     * Creates a new, default, UIMenu
     * object.
     */
    public UIMenu() {
        MenuKeyboard.addInterceptor(this);
        MenuMouse.addInterceptor(this);
    }

    /**
     * Creates a new UIMenu.
     * @param x The X value of the location of this menu.
     * @param y The Y value of the location of this menu.
     */
    public UIMenu(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new UIMenu object.
     * @param x The X value of the location of this menu.
     * @param y The Y value of the location of this menu.
     * @param width The width of this menu object.
     * @param height The height of this menu object.
     */
    public UIMenu(int x, int y, int width, int height) {
        this();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the count of components in this menu.
     * @return Number of components in this menu.
     */
    public int getComponentCount() {
        return componentList.size();
    }

    /**
     * Tells whether there is a focused component.
     * @return Whether there is a focused component.
     */
    public boolean hasFocusedComponent() {
        return focusedComponent >= 0 && focusedComponent < componentList.size();
    }

    /**
     * Gets the focused component of this menu.
     * @return The focused component of this menu.
     */
    public UIComponent getFocusedComponent() {
        return focusedComponent < 0 || focusedComponent >= componentList.size()
                ? null : componentList.get(focusedComponent);
    }

    /**
     * Gets the UIComponent within the specified
     * location.
     * @param x The X value of the location of a component.
     * @param y The Y value of the location of a component.
     * @return A UIComponent at that location (null if none)
     */
    public int getComponentOn(int x, int y) {
        for(int i = 0; i < componentList.size(); i++) {
            UIComponent c = componentList.get(i);
            if(x >= c.getX() && x <= c.getX() + c.getWidth()
                    && y >= c.getY() && y <= c.getY() + c.getHeight())
                return i;
        }
        return -1;
    }

    /**
     * Adds a component to this menu.
     * @param component A component to add.
     */
    public void addComponent(UIComponent component) {
        if(component != null)
            componentList.add(component);
    }

    /**
     * Sets the focused component of this menu.
     * @param index New index pointing to the target component to be focused.
     */
    public void setFocusedComponent(int index) {
        focusedComponent = index;
    }

    /**
     * Sets the focused component to the that specified component.
     * @param component The component to set focused.
     */
    public void setFocusedComponent(UIComponent component) {
        focusedComponent = -1;
        for(int i = 0; i < componentList.size(); i++) {
            if (componentList.get(i).equals(component)) {
                focusedComponent = i;
                break;
            }
        }
    }

    /**
     * Attempts to find a component within the specified
     * position and set it as the focused component.
     * @param x X value of the position of possible component to focus.
     * @param y Y value of the position of possible component to focus.
     */
    public void setFocusedComponentOn(int x, int y) {
        focusedComponent = -1;
        for(int i = 0; i < componentList.size(); i++) {
            UIComponent c = componentList.get(i);
            if( (x >= c.getX() && x <= c.getX() + c.getWidth())
                    && (y >= c.getY() && y <= c.getY() + c.getHeight()) ) {
                focusedComponent = i;
                break;
            }
        }
    }

    /**
     * Gets the X value of the location of this menu.
     * @return The X value of the location of this menu.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X value of the location of this menu.
     * @param x New X value of the location of this menu.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the Y value of the location of this menu.
     * @return The Y value of the location of this menu.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y value of the location of this menu.
     * @param y New Y value of the location of this menu.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the width of this menu.
     * @return Width of this menu.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of this menu.
     * @param width New width of this menu.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of this menu.
     * @return Height of this menu.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of this menu.
     * @param height New height of this menu.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Requests this menu object to poll events
     * from the mouse and update the menu as required.
     */
    public abstract void update();

    /**
     * Renders this menu onto the screen.
     * @param graphics The graphics object to use to render this menu.
     */
    public abstract void render(Graphics graphics);
}
