package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.FlowPosition;
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
public class Background implements FlowPosition.Listener {

    /**
     * Enumeration for the specific mode (movement)
     * of this background on the display.
     */
    public enum Mode {
        MOUSE_TRACK, // Tracks the mouse.
        FLOW_POSITION_TRACK // Bound to a flow position object and uses it's position.
    }

    private Mode bgMode = Mode.MOUSE_TRACK; // The behaviour of this background.
    private Image background; // The image to display in the background.
    private float horizontalRange = 30; // The horizontal range of movement of this background.
    private float verticalRange = 30; // The vertical range of movement of this background.
    private float horizontalAnchor = 0; // The horizontal anchor of this background.
    private float verticalAnchor = 0; // The vertical anchor of this background.
    private float x; // The x disposition of this background.
    private float y; // The y disposition of this background.
    private int roamSpace; // The amount of space this background has to roam.
    private boolean inverse = false; // Whether the movement of the background is opposite of anchors.

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
     * Sets the movement range of this background object.
     * @param horizontal The horizontal movement range.
     * @param vertical The vertical movement range.
     */
    public void setRange(float horizontal, float vertical) {
        horizontalRange = Math.max(horizontal, 1);
        verticalRange = Math.max(vertical, 1);
    }

    /**
     * Sets the behaviour of this background object.
     * @param mode The mode (movement behaviour) of this background.
     */
    public void setMode(Mode mode) {
        bgMode = mode;
    }

    /**
     * Sets whether the background will move in the
     * opposite direction, and away from the anchor
     * (aka. Mouse cursor or entity position).
     * @param v Whether the background is inverse.
     */
    public void setInverse(boolean v) {
        inverse = v;
    }

    /**
     * Updates the disposition of this background
     * relative to the mouse position in the scene.
     */
    public void update() {
        float w = bgMode == Mode.MOUSE_TRACK ? Game.activeGame.getResolutionWidth() : horizontalRange;
        float h = bgMode == Mode.MOUSE_TRACK ? Game.activeGame.getResolutionHeight() : verticalRange;
        float x = bgMode == Mode.MOUSE_TRACK ? MenuMouse.getX() : horizontalAnchor;
        float y = bgMode == Mode.MOUSE_TRACK ? MenuMouse.getY() : verticalAnchor;

        this.x = inverse ? (w - x) / w * roamSpace : x / w * roamSpace;
        this.y = inverse ? y / h * roamSpace : (h - y) / h * roamSpace;
    }

    /**
     * Renders this background on to the screen.
     * @param graphics Graphics object used to render the background.
     */
    public void render(Graphics graphics) {
        graphics.drawImage(background, x - roamSpace, -y);
    }

    /**
     * Event captured from a FlowPosition object that
     * has dispatched a change in the X value.
     * @param now The new intermediate X value.
     */
    @Override
    public void horizontalChange(float now) {
        horizontalAnchor = now;
    }

    /**
     * Event captured from a FlowPosition object that
     * has dispatched a change in the Y value.
     * @param now The new intermediate Y value.
     */
    @Override
    public void verticalChange(float now) {
        verticalAnchor = now;
    }
}
