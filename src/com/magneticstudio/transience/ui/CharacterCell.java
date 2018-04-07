package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.FloatDimension;
import com.magneticstudio.transience.util.FloatPoint;
import com.magneticstudio.transience.util.IntDimension;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

/**
 * This class simply display a character instead of an
 * image as a graphical element.
 *
 * @author Max
 */
public final class CharacterCell implements GraphicalElement {

    private static final int VERTICAL_ADJUST = -3; // The vertical adjustment when rendering.

    private UnicodeFont font; // The font name used to render the character
    private String character = "O"; // The character in the character cell.
    private Color color = new Color(255, 255, 255, 255); // The color to render the character as.

    private int width = 64; // The width of the area of where the character is rendered.
    private int height = 64; // The height of the area of where the character is rendered.

    /**
     * Creates a new character cell object
     * with default values.
     */
    public CharacterCell() {

    }

    /**
     * Creates a new CharacterCell object with
     * the specified font to use and the character
     * to display.
     * @param font The font name to use for displaying the character.
     * @param character The character to display.
     */
    public CharacterCell(UnicodeFont font, char character) {
        this.font = font;
        this.character = Character.toString(character);
        this.width = font.getWidth(this.character);
        this.height = font.getHeight(this.character);
    }

    /**
     * Gets the font of this CharacterCell.
     * @return The font of this character cell.
     */
    public UnicodeFont getFont() {
        return font;
    }

    /**
     * Sets the font of this CharacterCell.
     * @param newFont The new font for this character cell.
     */
    public void setFont(UnicodeFont newFont) {
        font = newFont;
    }

    /**
     * Gets the character of this character cell.
     * @return The character of this character cell.
     */
    public char getCharacter() {
        return character.charAt(0);
    }

    /**
     * Sets a new character for this character cell.
     * @param newCharacter The new character for this character cell.
     */
    public void setCharacter(char newCharacter) {
        character = Character.toString(newCharacter);
    }

    /**
     * Gets the dimensions of this character
     * cell object.
     * @return Dimensions of the character cell.
     */
    @Override
    public IntDimension getDimensions() {
        return new IntDimension(width, height);
    }

    @Override
    public void setDimensions(IntDimension dimensions) {
        this.width = dimensions.getWidth();
        this.height = dimensions.getHeight();
    }

    /**
     * Sets the dimensions of this character cell's area.
     * @param width The new width of this graphical element.
     * @param height The new height of this graphical element.
     */
    @Override
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of this character cell's area.
     * @return The width of the character cell's area.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the character cell's area.
     * @param width New width of this graphical element.
     */
    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the character cell's area.
     * @return Height of the character cell's area.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of this character cell's area.
     * @param height New height of this graphical element.
     */
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the alpha of this character.
     * @return Alpha of this character.
     */
    @Override
    public float getAlpha() {
        return color.a;
    }

    /**
     * Sets the alpha of this character.
     * @param alpha New alpha of this graphical element.
     */
    @Override
    public void setAlpha(float alpha) {
        color.a = Math.max(Math.min(alpha, 1.0f), 0.0f);
    }

    /**
     * Gets the color of this character cell object.
     * @return Color of this character cell object.
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Sets a new color for this character cell object.
     * @param newColor A new color for this character cell object.
     */
    @Override
    public void setColor(Color newColor) {
        color.r = newColor.r;
        color.g = newColor.g;
        color.b = newColor.b;
        font = Res.modifyFont(
            font,
            newColor,
            Res.USE_DEFAULT,
            Res.USE_DEFAULT,
            Res.USE_DEFAULT
        );
    }

    /**
     * Renders the character onto the screen.
     * @param graphics The graphics used to render anything onto the main screen.
     * @param x The X value of the position that this object is supposed to be rendered at.
     * @param y The Y value of the position that this object is supposed to be rendered at.
     * @param centerSurround Whether or not the x and y are based around the center of the element.
     */
    @Override
    public void render(Graphics graphics, float x, float y, boolean centerSurround) {
        float rx = centerSurround ? x + (width / 2) - (font.getWidth(character) / 2) : x;
        float ry = centerSurround ? y + (height / 2) - (font.getHeight(character) / 2) + VERTICAL_ADJUST : y;

        font.drawString(rx, ry, character);
    }
}
