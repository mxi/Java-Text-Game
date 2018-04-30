package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Displayable;
import com.magneticstudio.transience.ui.Game;
import org.newdawn.slick.Graphics;

import java.util.Stack;

/**
 * This class simply handles item stacks
 * for the player's inventories.
 *
 * @author Max
 */
public class InventoryStack<T extends Item> implements Displayable {

    /**
     * Creates a new weapon inventory stack.
     * @param <W> The type of weapon the inventory stack contains.
     * @return The inventory stack fit to hold a weapon.
     */
    public static <W extends Weapon> InventoryStack<W> createWeaponStack() {
        return new InventoryStack<>(1);
    }

    private static final float WAVE_INTENSITY = 10f; // Intensity of the floating item.
    private static final int VERTICAL_ADJUSTMENT = 10; // The adjustment for item rendering.

    private int capacity = 16; // The capacity this inventory stack.
    private Stack<T> items = new Stack<>(); // The list of items in this stack.
    private double sinParameter = 0;
    private long lastRenderTime = System.currentTimeMillis() + Game.rng.nextInt(1000);

    /**
     * Creates a new default inventory stack.
     */
    public InventoryStack() {

    }

    /**
     * Creates a new inventory stack of specified capacity.
     * @param nCapacity The capacity of the inventory stack.
     */
    public InventoryStack(int nCapacity) {
        capacity = Math.max(1, nCapacity);
    }

    /**
     * Gets the capacity of this inventory stack.
     * @return The capacity of the inventory stack.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the new capacity for this inventory stack.
     * @param nCapacity New capacity for the inventory stack.
     */
    public void setCapacity(int nCapacity) {
        for(int i = capacity - nCapacity; i > 0; i--) {
            if(items.size() == 0)
                break;
            items.pop();
        }
        capacity = Math.max(1, nCapacity);
    }

    /**
     * Gets the item count of this inventory stack.
     * @return The item count of this inventory stack.
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Adds an item to this inventory stack.
     * @param item The item to add to this inventory stack.
     */
    public void push(T item) {
        if(items.size() < capacity)
            items.push(item);
    }

    /**
     * Gets the first item in the inventory stack.
     * @return First item in the inventory stack.
     */
    public Item peek() {
        if(items.size() == 0)
            return null;
        else
            return items.get(0);
    }

    /**
     * Removes the next item from the stack.
     */
    public void pop() {
        if(items.size() != 0)
            items.pop();
    }

    /**
     * Renders a preview of this inventory stack.
     * @param graphics The graphics used for drawing on main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        if(items.size() == 0)
            return;

        boolean tempAntiAlias = graphics.isAntiAlias();
        graphics.setAntiAlias(false);
        sinParameter += (float) (System.currentTimeMillis() - lastRenderTime) / 250f;
        float yOffset = (float) Math.sin(sinParameter) * WAVE_INTENSITY;
        items.peek().render(graphics, x, y + yOffset + VERTICAL_ADJUSTMENT, centerSurround);
        lastRenderTime = System.currentTimeMillis();
        graphics.setAntiAlias(tempAntiAlias);
    }
}
