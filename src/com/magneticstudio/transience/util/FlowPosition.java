package com.magneticstudio.transience.util;

import com.magneticstudio.transience.ui.LogicalElement;

/**
 * This class provides very a convenient
 * way to create smooth motion when moving
 * objects within this game in the render
 * window.
 *
 * @author Max
 */
public class FlowPosition implements LogicalElement {

    private float towardsX = 0; // The value of X that this motion tends towards.
    private float towardsY = 0; // The value of Y that this motion tends towards.

    private float nowX = 0; // The current X value of the intermediate motion.
    private float nowY = 0; // The current Y value of the intermediate motion.

    private float modifyX = 0; // The amount to modify X by each millisecond.
    private float modifyY = 0; // The amount to modify Y by each millisecond.

    private float timeMillis = 500; // The time in milliseconds the old position has to transfer to the new position.
    private float timeElapsed = 0; // The time elapsed transitioning to the target position.

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
    public FloatPoint getPosition() {
        return new FloatPoint(towardsX, towardsY);
    }

    /**
     * Sets the position that this motion tends towards.
     * @param dest The destination of this motion.
     */
    public void setPosition(FloatPoint dest) {
        setPosition(dest.getX(), dest.getY());
        timeElapsed = 0;
    }

    /**
     * Sets the position that this motion tends towards.
     * @param x The X value the position tends towards.
     * @param y The Y value the position tends towards.
     */
    public void setPosition(float x, float y) {
        setTargetX(x);
        setTargetY(y);
        timeElapsed = 0;
    }

    /**
     * Gets the target X value.
     * @return Target X value.
     */
    public float getTargetX() {
        return towardsX;
    }

    /**
     * Sets the target X value.
     * @param x Target X value.
     */
    public void setTargetX(float x) {
        modifyX = (x - nowX) / timeMillis;
        towardsX = x;
        timeElapsed = 0;
    }

    /**
     * Gets the target Y value.
     * @return Target Y value.
     */
    public float getTargetY() {
        return towardsY;
    }

    /**
     * Sets the target Y value.
     * @param y Target Y value.
     */
    public void setTargetY(float y) {
        modifyY = (y - nowY) / timeMillis;
        towardsY = y;
        timeElapsed = 0;
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

    /**
     * Gets the target time in milliseconds of
     * the transition from old position to new
     * position.
     * @return The target transition time in milliseconds.
     */
    public float getTargetTime() {
        return timeMillis;
    }

    /**
     * Sets the transition time of this object
     * if it's not moving.
     * @param newTime The new time in milliseconds for transitioning.
     */
    public void setTransitionTime(int newTime) {
        if(towardsX == nowX && towardsY == nowY)
            timeMillis = Math.max(newTime, 1);
    }

    /**
     * Gets the elapsed time of this transition.
     * @return ELapsed time of transition.
     */
    public float getElapsedTime() {
        return timeElapsed;
    }

    /**
     * Checks whether this position is
     * being modified.
     * @return Whether the position is being modified.
     */
    public boolean isMoving() {
        return towardsX != nowX || towardsY != nowY;
    }

    /**
     * Updates this FlowPosition object.
     * Sets the target amount of time to complete
     * the transition.
     * @param millis New amount of time in milliseconds.
     */
    public void setTargetTime(float millis) {
        timeMillis = Math.max(millis, 1);
    }

    /**
     * Updates this flow position object.
     * @param milliseconds The elapsed time since last update.
     */
    @Override
    public void update(int milliseconds) {
        if(timeElapsed >= timeMillis)
            return;

        timeElapsed += milliseconds;

        if(timeElapsed >= timeMillis) {
            nowX = towardsX;
            nowY = towardsY;
            modifyX = 0;
            modifyY = 0;
        }
        else {
            nowX += modifyX * milliseconds;
            nowY += modifyY * milliseconds;
        }
    }

    @Override
    public String toString() {
        return "(" + nowX + ", " + nowY + " go to " + towardsX + ", " + towardsY + ")";
    }
}