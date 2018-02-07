package com.magneticstudio.transience.util;

/**
 * This class provides very a convenient
 * way to create smooth motion when moving
 * objects within this game in the render
 * window.
 *
 * @author Max
 */
public class FlowPosition {

    private int towardsX = 0; // The value of X that this motion tends towards.
    private int towardsY = 0; // The value of Y that this motion tends towards.

    private float nowX = 0; // The current X value of the intermediate motion.
    private float nowY = 0; // The current Y value of the intermediate motion.

    private float modifyX; // The amount to modify X by each millisecond to reach the new position.
    private float modifyY; // The amount to modify Y by each millisecond to reach the new position.

    private float timeMillis = 500; // The time in milliseconds the old position has to transfer to the new position.

    /**
     * Creates a new flow position object
     * with default values set.
     */
    public FlowPosition() {

    }

    /**
     * Creates a new FlowPosition object
     * @param x The initial X value.
     * @param y The initial Y value.
     */
    public FlowPosition(int x, int y) {
        towardsX = x;
        towardsY = y;

        nowX = x;
        nowY = y;
    }

    /**
     * Gets the position that this motion tends towards.
     * @return The position that the motion tends towards.
     */
    public IntPoint getPosition() {
        return new IntPoint(towardsX, towardsY);
    }

    /**
     * Sets the position that this motion tends towards.
     * @param dest The destination of this motion.
     */
    public void setPosition(IntPoint dest) {
        modifyX = (float) (dest.getX() - towardsY) / timeMillis;
        modifyY = (float) (dest.getY() - towardsY) / timeMillis;
        towardsX = dest.getX();
        towardsY = dest.getY();
    }

    /**
     * Sets the position that this motion tends towards.
     * @param x The X value the position tends towards.
     * @param y The Y value the position tends towards.
     */
    public void setPosition(int x, int y) {
        modifyX = (float) (x - towardsY) / timeMillis;
        modifyY = (float) (y - towardsY) / timeMillis;
        towardsX = x;
        towardsY = y;
    }

    /**
     * Gets the target X value.
     * @return Target X value.
     */
    public int getTargetX() {
        return towardsX;
    }

    /**
     * Sets the target X value.
     * @param x Target X value.
     */
    public void setTargetX(int x) {
        towardsX = x;
    }

    /**
     * Gets the target Y value.
     * @return Target Y value.
     */
    public int getTargetY() {
        return towardsY;
    }

    /**
     * Sets the target Y value.
     * @param y Target Y value.
     */
    public void setTargetY(int y) {
        towardsY = y;
    }

    /**
     * Gets the value of X as the motion is occurring.
     * @return Intermediate X value.
     */
    public float getIntermediateX() {
        return nowX;
    }

    /**
     * Gets the value of Y as the motion is occurring.
     * @return Intermediate Y value.
     */
    public float getIntermediateY() {
        return nowY;
    }
}