package com.magneticstudio.transience.util;

/**
 * A class used to represent two values for width
 * and height.
 * @author Max
 */
public class IntDimension {

    private int width; // width value.
    private int height; // height value.

    /**
     * Default constructor of IntDimension that initializes
     * width and height to 0 (default).
     */
    public IntDimension() {
        width = 0;
        height = 0;
    }

    /**
     * Constructor for IntDimension that initializes width and
     * height to the specified amount.
     * @param w Width of the IntDimension object.
     * @param h Height of the IntDimension object.
     */
    public IntDimension(int w, int h) {
        width = w;
        height = h;
    }

    /**
     * @return Width of this IntDimension object.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width Width for this IntDimension object.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return Height of this IntDimension object.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height Height for this IntDimension object.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets a string representation of this object.
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return "(" + width + ", " + height + ")";
    }
}