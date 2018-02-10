package com.magneticstudio.transience.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * An image generator that generates a vignette
 * from parameters specified through the constructor
 * or function calls.
 *
 * @author Max
 */
public class RadialVignetteGenerator implements ImageGenerator {

    private int imageWidth = 560; // Width of the image to generate the effect on.
    private int imageHeight = 560; // Height of the image to generate the effect on.

    private float centerX = 560 / 2, centerY = 560 / 2; // The center of the vignette.
    private float radius = 128; // Radius of the vignette.
    private float softness = .5f; // The hardness of the vignette.
    private float upscale = 1.0f; // The amount to scale the image after generating the vignette.

    private Color color = Color.white; // The color of the vignette.

    /**
     * Creates a new RadialVignetteGenerator object
     * with default values.
     */
    public RadialVignetteGenerator() {

    }

    /**
     * Creates a new RadialVignetteGenerator object
     * with the specified with and height for
     * the image.
     * @param imWidth Width of the image.
     * @param imHeight Height of the image.
     */
    public RadialVignetteGenerator(int imWidth, int imHeight) {
        imageWidth = imWidth;
        imageHeight = imHeight;
    }

    /**
     * Creates a new RadialVignetteGenerator object
     * with the specified with and height for
     * the image as well as the center of the
     * vignette and the radius.
     * @param imWidth Width of the image.
     * @param imHeight Height of the image.
     * @param cx X value of the position of the center of the vignette.
     * @param cy Y value of the position of the center of the vignette.
     * @param rad The radius of the vignette.
     */
    public RadialVignetteGenerator(int imWidth, int imHeight, float cx, float cy, float rad) {
        imageWidth = imWidth;
        imageHeight = imHeight;
        centerX = cx;
        centerY = cy;
        radius = rad;
    }

    /**
     * Gets the color of this vignette.
     * @return Color of this vignette.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of this vignette.
     * @param newColor New color of this vignette.
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Gets the image width.
     * @return Image width.
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * Sets the image width.
     * @param imageWidth Image width.
     */
    public void setImageWidth(int imageWidth) {
        this.imageWidth = Math.max(Math.min(imageWidth, 1920), 1);
    }

    /**
     * Gets the image height.
     * @return Image height.
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Sets the image height.
     * @param imageHeight Image height.
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = Math.max(Math.min(imageHeight, 1080), 1);
    }

    /**
     * Gets the X value of the center.
     * @return X value of the center.
     */
    public float getCenterX() {
        return centerX;
    }

    /**
     * Sets the center x value.
     * @param centerX Center x value.
     */
    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    /**
     * Gets the Y value of the center.
     * @return Y value of the center.
     */
    public float getCenterY() {
        return centerY;
    }

    /**
     * Sets the center y value.
     * @param centerY Center y value.
     */
    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    /**
     * Gets the radius of the vignette.
     * @return Radius of the vignette.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the vignette.
     * @param radius Radius of the vignette.
     */
    public void setRadius(float radius) {
        this.radius = Math.max(radius, 1);
    }

    /**
     * Gets the softness of the vignette.
     * @return Softness of the vignette.
     */
    public float getSoftness() {
        return softness;
    }

    /**
     * Sets the softness of this vignette where
     * the specified value is in range of 0 to 1;
     * 0 being the hardest; 1 being the softest.
     * @param softness Softness in range of 0 to 1.
     */
    public void setSoftness(float softness) {
        this.softness = Math.max(Math.min(softness, 1.0f), 0.0f);
    }

    /**
     * Gets the hardness of the vignette.
     * @return Hardness of the vignette.
     */
    public float getHardness() {
        return 1.0f - softness;
    }

    /**
     * Sets the hardness of the vignette, which
     * is the inverse of the softness.
     * @param hardness Hardness in range from 0 to 1.
     */
    public void setHardness(float hardness) {
        this.softness = 1.0f - Math.max(Math.min(hardness, 1.0f), 0.0f);
    }

    /**
     * Gets the upscale value of this vignette
     * generator.
     * @return Upscale value.
     */
    public float getUpscale() {
        return upscale;
    }

    /**
     * Sets the upscale value of this vignette
     * @param upscale The new upscale value.
     */
    public void setUpscale(float upscale) {
        this.upscale = upscale;
    }

    /**
     * Renders a vignette onto an image object.
     * @return The image with an effect rendered onto it.
     */
    @Override
    public Image generate() throws SlickException {
        Image canvas = new Image(imageWidth, imageHeight);
        Graphics graphics = canvas.getGraphics();

        double lowestRadius = radius - (radius * softness);
        double softnessRadRange = radius - lowestRadius;

        for(int y = 0; y < imageHeight; y++) {
            for(int x = 0; x < imageWidth; x++) {
                double curRadius = Math.sqrt( Math.pow( x - centerX, 2 ) + Math.pow( y - centerY, 2 ));
                float alpha = 1f;
                if(curRadius <= radius && curRadius >= lowestRadius)
                    alpha = (float) ((curRadius - lowestRadius) / softnessRadRange);
                else if(curRadius < lowestRadius)
                    alpha = 0f;

                Color adjusted = new Color(color.r, color.g, color.b, alpha);
                graphics.setColor(adjusted);
                graphics.drawRect(x, y, 1, 1);
            }
        }
        if(upscale == 1)
            return canvas;
        else
            return canvas.getScaledCopy(upscale);
    }
}
