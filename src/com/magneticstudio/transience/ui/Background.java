package com.magneticstudio.transience.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * This class displays a background beneath the game or
 * a menu that may be animated to follow the mouse cursor
 * or any other effects that may be implemented in the future.
 *
 * @author Max
 */
public class Background {

    private Image background; // The image to display in the background.
    private float x; // The x disposition of this background.
    private float y; // The y disposition of this background.
    private int roamSpace; // The amount of space this background has to roam.

    /**
     * Creates a new background object with the default
     * 100 pixels roaming space.
     * @param image The image to use as the background.
     */
    public Background(Image image) {
        roamSpace = 100;
        background = image.getScaledCopy(
             Game.activeGame.getResolutionWidth() + roamSpace,
             Game.activeGame.getResolutionHeight() + roamSpace
        );
    }

    /**
     * Gets the roam space of this background.
     * @return Roam space of this background.
     */
    public int getRoamSpace() {
        return roamSpace;
    }

    /**
     * Sets the roaming space of this background.
     * @param roamSpace New roam space of this background.
     */
    public void setRoamSpace(int roamSpace) {
        this.roamSpace = Math.max(roamSpace, 0);
        background = background.getScaledCopy(
            Game.activeGame.getResolutionWidth() + this.roamSpace,
            Game.activeGame.getResolutionHeight() + this.roamSpace
        );
    }

    /**
     * Updates the disposition of this background
     * relative to the mouse position in the scene.
     */
    public void update() {
        x = ((float) Mouse.getX() / (float) Game.activeGame.getResolutionWidth()) * (float) roamSpace;
        y = ((float) Mouse.getY() / (float) Game.activeGame.getResolutionHeight()) * (float) roamSpace;
    }

    /**
     * Renders this background on to the screen.
     * @param graphics Graphics object used to render the background.
     */
    public void render(Graphics graphics) {
        graphics.drawImage(background, x - roamSpace, -y);
    }
}
