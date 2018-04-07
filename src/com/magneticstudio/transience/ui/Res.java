package com.magneticstudio.transience.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * This class is a "redesign" of the previous
 * Cache class, but this time it doesn't store anything
 * and just provides straight forward utility functions
 * to clean up/aid code.
 *
 * The previous method became way too confusing, even very
 * early in the game's codebase.
 *
 * @author Max.
 */
public class Res {

    public static final byte USE_DEFAULT = -1; // Value for using a default setting.
    public static final byte TRUE = 1; // Value for a true flag.
    public static final byte FALSE = 0; // Value for a false flag.

    private static final byte DEFAULT_FONT_SIZE = 16; // Default font size.
    private static final boolean DEFAULT_BOLD = false; // Default bold setting.
    private static final boolean DEFAULT_ITAL = false; // Default italicize setting.
    private static final String FONT_DIRECTORY = "resources/fonts/"; // The directory to the font file collection.

    /**
     * Creates a new UnicodeFont object by reading
     * the specified file from the "resources/fonts/" directory.
     * @param fileName The name of the font to load (directory is not needed, just the file name)
     * @param color The color of the font.
     * @param size The preferred size of the font.
     * @param bold Whether the font should be bold.
     * @param italicized Whether the font should be italicised.
     * @return A new UnicodeFont object with the specified settings.
     */
    public static UnicodeFont loadFont(String fileName, Color color, int size, byte bold, byte italicized) {
        try {
            UnicodeFont font = new UnicodeFont(
                    FONT_DIRECTORY + fileName,
                    size <= USE_DEFAULT ? DEFAULT_FONT_SIZE : size,
                    bold <= USE_DEFAULT ? DEFAULT_BOLD : (bold == TRUE),
                    bold <= USE_DEFAULT ? DEFAULT_ITAL : (italicized == TRUE)
            );
            if(color == null)
                font.getEffects().add(new ColorEffect(new java.awt.Color(255, 255, 255)));
            else
                font.getEffects().add(new ColorEffect(new java.awt.Color(color.r, color.g, color.b, color.a)));
            font.addAsciiGlyphs();
            font.loadGlyphs();
            return font;
        }
        catch(SlickException e) {
            return null;
        }
    }

    /**
     * Modifies an existing UnicodeFont and returns a new
     * one with the modified settings.
     * @param toMod The font to modify.
     * @param color The new color of the font (null for no change).
     * @param size The new size of the font (anything <= 0 will not change size)
     * @param bold Whether the new font should be bold.
     * @param italicized Whether the new font should be italicised.
     * @return A new modified version of a UnicodeFont.
     */
    public static UnicodeFont modifyFont(UnicodeFont toMod, Color color, int size, byte bold, byte italicized) {
        try {
            UnicodeFont font = new UnicodeFont(
                toMod.getFont(),
                size <= USE_DEFAULT ? toMod.getFont().getSize() : size,
                bold <= USE_DEFAULT ? toMod.getFont().isBold() : (bold == TRUE),
                bold <= USE_DEFAULT ? toMod.getFont().isItalic() : (italicized == TRUE)
            );
            if(color == null)
                font.getEffects().addAll(toMod.getEffects());
            else
                font.getEffects().add(new ColorEffect(new java.awt.Color(color.r, color.g, color.b, color.a)));
            font.addAsciiGlyphs();
            font.loadGlyphs();
            return font;
        }
        catch(SlickException e) {
            return null;
        }
    }
}
