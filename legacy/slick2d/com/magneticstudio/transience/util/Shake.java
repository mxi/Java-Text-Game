package com.magneticstudio.transience.util;


/**
 * This class provides easy offset modifiers
 * to apply to graphical elements and make them
 * appear to shake.
 *
 * @author Max
 */
public class Shake {

    private static final float TRIG_ITERATOR_CONSTANT = 2 * (float) Math.PI / 1000f;

    private int durationLeft = 0;

    private float frequency; // Offset cycles per second.
    private float amplitude; // Offset strength.

    private float freqChange;
    private float ampChange;

    private float trigIterator = 0f;
    private float verticalOffset = 0f;
    private float horizontalOffset = 0f;
    private float rotationOffset = 0f;

    private float verticalCoefficient = 1f;
    private float horizontalCoefficient = 0.25f;
    private float rotationCoefficient = 0.625f;

    /**
     * Creates a standstill Shake object.
     */
    public Shake() {

    }

    /**
     * Initiates the shaking process.
     * @param amplitude The initial amplitude.
     * @param frequency The initial frequency.
     * @param duration The duration in milliseconds (during this time frequency and amplitude will diminish)
     */
    public void start(float amplitude, float frequency, int duration) {
        this.amplitude = amplitude;
        this.frequency = frequency;
        ampChange = Math.abs(amplitude / (float) duration);
        freqChange = Math.abs(frequency / (float) duration);
        durationLeft = duration;
    }

    /**
     * Cancels the current shake.
     */
    public void cancel() {
        durationLeft = 0;
        trigIterator = 0f;
        verticalOffset = 0f;
        horizontalOffset = 0f;
        rotationOffset = 0f;
    }

    /**
     * Gets the vertical coefficient (frequency constant for the vertical offset).
     * @return The vertical coefficient.
     */
    public float getVerticalCoefficient() {
        return verticalCoefficient;
    }

    /**
     * Sets the vertical coefficient.
     * @see Shake#getVerticalCoefficient() Definition.
     * @param verticalCoefficient The new vertical coefficient.
     */
    public void setVerticalCoefficient(float verticalCoefficient) {
        this.verticalCoefficient = verticalCoefficient;
    }

    /**
     * Gets the horizontal coefficient (frequency constant for the horizontal offset).
     * @return The horizontal coefficient.
     */
    public float getHorizontalCoefficient() {
        return horizontalCoefficient;
    }

    /**
     * Sets the horizontal coefficient.
     * @see Shake#getHorizontalCoefficient() Definition.
     * @param horizontalCoefficient The new horizontal coefficient.
     */
    public void setHorizontalCoefficient(float horizontalCoefficient) {
        this.horizontalCoefficient = horizontalCoefficient;
    }

    /**
     * Gets the rotation coefficient (frequency constant for the rotation offset).
     * @return The rotation coefficient.
     */
    public float getRotationCoefficient() {
        return rotationCoefficient;
    }

    /**
     * Sets the rotation coefficient.
     * @see Shake#getRotationCoefficient() Definition.
     * @param rotationCoefficient The new rotation coefficient.
     */
    public void setRotationCoefficient(float rotationCoefficient) {
        this.rotationCoefficient = rotationCoefficient;
    }

    /**
     * Gets the horizontal offset from the Shake.
     * @return Horizontal offset.
     */
    public float getHorizontalOffset() {
        return horizontalOffset * amplitude;
    }

    /**
     * Gets the vertical offset from the shake.
     * @return Vertical offset.
     */
    public float getVerticalOffset() {
        return verticalOffset * amplitude;
    }

    /**
     * Gets the rotation offset from the shake.
     * (Radians)
     * @return Rotation shake.
     */
    public float getRotationOffset() {
        return rotationOffset * amplitude;
    }

    /**
     * Updates the offsets based on elapsed time.
     * @param elapsed Time elapsed in milliseconds.
     */
    public void update(int elapsed) {
        if(durationLeft <= 0)
            return;

        verticalOffset = (float) Math.sin(trigIterator) * verticalCoefficient;
        horizontalOffset = (float) Math.cos(trigIterator) * horizontalCoefficient;
        rotationOffset = (float) Math.sin(trigIterator) * rotationCoefficient;

        trigIterator += elapsed * (TRIG_ITERATOR_CONSTANT * frequency);
        frequency -= freqChange * elapsed;
        amplitude -= ampChange * elapsed;

        durationLeft -= elapsed;
        if(durationLeft <= 0) {
            trigIterator = 0f;
            verticalOffset = 0f;
            horizontalOffset = 0f;
            rotationOffset = 0f;
        }
    }
}
