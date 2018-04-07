package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.FloatDimension;
import com.magneticstudio.transience.util.FloatPoint;
import com.magneticstudio.transience.util.IntDimension;
import org.newdawn.slick.Color;

/**
 * This interface extends the Displayable
 * interface and specifies some new functions
 * that much be implemented by graphical elements.
 *
 * @author Max
 */
public interface GraphicalElement extends Displayable {

    /**
     * Gets the dimensions of this graphical element.
     * @return Dimensions of this graphical element.
     */
    IntDimension getDimensions();

    /**
     * Sets the dimensions for this graphical element.
     * @param dimensions Dimensions for this graphical element.
     */
    void setDimensions(IntDimension dimensions);

    /**
     * Sets the dimensions for this graphical element.
     * @param width The new width of this graphical element.
     * @param height The new height of this graphical element.
     */
    void setDimensions(int width, int height);

    /**
     * Gets the width of this graphical element.
     * @return The width of this graphical object.
     */
    int getWidth();

    /**
     * Sets the width of this graphical element.
     * @param width New width of this graphical element.
     */
    void setWidth(int width);

    /**
     * Gets the height of this graphical element.
     * @return The height of this graphical element.
     */
    int getHeight();

    /**
     * Sets the height of this graphical element.
     * @param height New height of this graphical element.
     */
    void setHeight(int height);

    /**
     * Gets the alpha of this graphical element.
     * @return The alpha of this graphical element.
     */
    float getAlpha();

    /**
     * Sets the alpha of this graphical element.
     * @param alpha New alpha of this graphical element.
     */
    void setAlpha(float alpha);

    /**
     * Gets the color of this graphical element.
     * @return Color of this element.
     */
    Color getColor();

    /**
     * Sets the color of this graphical element.
     * @param newColor New color of this graphical element.
     */
    void setColor(Color newColor);
}
