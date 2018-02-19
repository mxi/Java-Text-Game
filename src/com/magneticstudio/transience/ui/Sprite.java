package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.IntDimension;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * This class is a collection of images that may
 * be rendered in order to create an animation.
 *
 * @author Max
 */
public class Sprite implements GraphicalElement {

    private Image[] images; // The array of images produced by the spriteSheet object.
    private int currentFrame = 0; // The index of the current frame being rendered.

    private long lastFrame = 0; // The point in the last frame was rendered.
    private int frameRateWaitTime = 250; // The amount of time to wait before going to next frame.
    private int width; // The width of each frame.
    private int height; // The height of each frame.

    /**
     * Creates a new sprite object from
     * an existing sheet image.
     * @param sheet The sprite sheet containing the collection of images.
     */
    public Sprite(Image sheet, int sheetWidth, int sheetHeight, int frameRate) {
        SpriteSheet spriteSheet = new SpriteSheet(sheet, sheetWidth, sheetHeight);
        images = new Image[spriteSheet.getVerticalCount() * spriteSheet.getHorizontalCount()];
        setWidth(sheetWidth);
        setHeight(sheetHeight);
        setFrameRate(frameRate);
        int frameLocation = 0;
        for(int y = 0; y < spriteSheet.getVerticalCount(); y++) {
            for(int x = 0; x < spriteSheet.getHorizontalCount(); x++) {
                images[frameLocation++] = spriteSheet.getSprite(x, y).getScaledCopy(width, height);
            }
        }
    }

    /**
     * Gets the width and height as an IntDimensions object.
     * @return Width and height of each frame in the sprite object.
     */
    @Override
    public IntDimension getDimensions() {
        return new IntDimension(width, height);
    }

    /**
     * Sets the width and height of this sprite object through
     * an IntDimension object.
     * @param newDimensions The new dimensions for the frames.
     */
    @Override
    public void setDimensions(IntDimension newDimensions) {
        setDimensions(newDimensions.getWidth(), newDimensions.getHeight());
    }

    /**
     * Sets the width and height of this sprite object through
     * regular specified ints.
     * @param width The new width of each frame.
     * @param height The new height of each frame.
     */
    @Override
    public void setDimensions(int width, int height) {
        setWidth(width);
        setHeight(height);
        float rotation = images[0].getRotation();
        for(int i = 0; i < images.length; i++) {
            images[i] = images[i].getScaledCopy(width, height);
            images[i].rotate(rotation);
        }
    }

    /**
     * Gets the width of each frame.
     * @return Width of each frame.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of each frame.
     * @param width Width of each frame.
     */
    @Override
    public void setWidth(int width) {
        this.width = Math.max(Math.min(width, 512), 8);
    }

    /**
     * Gets the height of each frame.
     * @return Height of each frame.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of each frame.
     * @param height Height of each frame.
     */
    @Override
    public void setHeight(int height) {
        this.height = Math.max(Math.min(height, 512), 8);
    }

    /**
     * Rotates all of the images in this collection
     * by a specified amount in degrees.
     * NOTE: Rotation goes to the right.
     * @param degreeAngle The amount to rotate in degrees.
     */
    public void rotate(float degreeAngle) {
        for(Image image : images)
            image.rotate(degreeAngle);
    }

    /**
     * Gets the frame-rate of this sprite.
     * @return The frame-rate of this sprite.
     */
    public int getFrameRate() {
        return 1000 / frameRateWaitTime;
    }

    /**
     * Sets the frame-rate of this sprite
     * and adjusts the duration of each
     * individual frame of the animation object.
     * @param frameRate The new frame rate.
     */
    public void setFrameRate(int frameRate) {
        this.frameRateWaitTime = 1000 / Math.max(Math.min(frameRate, 120), 1);
    }

    /**
     * Gets the alpha of each image in the image array.
     * @return Alpha of each image in the image array.
     */
    @Override
    public float getAlpha() {
        return images[0].getAlpha();
    }

    /**
     * Sets the opacity/alpha of each image
     * in this sprite.
     * @param alpha The alpha value for all images.
     */
    @Override
    public void setAlpha(float alpha) {
        for(Image image : images)
            image.setAlpha(alpha);
    }

    /**
     * Gets the color of this graphical element.
     * @return Color of this element.
     */
    @Override
    public Color getColor() {
        return null;
    }

    /**
     * Sets the color of this graphical element.
     * @param newColor New color of this graphical element.
     */
    public void setColor(Color newColor) {
        // Do nothing...
    }

    /**
     * Draws this sprite at a specified location.
     * (The location is oriented around the
     * center of each frame).
     * @param graphics The graphics object used to render anything on the main screen.
     * @param x The X value of the position to render the sprite in.
     * @param y The Y value of the position to render the sprite in.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        if(centerSurround)
            graphics.drawImage(images[currentFrame], x - (width / 2), y - (height / 2));
        else
            graphics.drawImage(images[currentFrame], x, y);
        next();
    }

    /**
     * Checks if the next frame needs to be fetched,
     * and if so, it will be fetched and the time
     * stamp of the last frame will be set to the time
     * of retrieval of the new frame.
     */
    private void next() {
        long now = System.currentTimeMillis();
        long difference = now - lastFrame;
        if(difference >= frameRateWaitTime) {
            currentFrame++;
            if(currentFrame == images.length)
                currentFrame = 0;

            lastFrame = now;
        }
    }
}
