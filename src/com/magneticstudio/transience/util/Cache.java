package com.magneticstudio.transience.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is designed to store common objects
 * to be able to reuse them again when needed.
 *
 * For example, if you load an image for a tile, your
 * don't want to keep reading the file over and over again
 * for the same image. Instead, allocating cache for that
 * image would make the process a lot faster.
 *
 * @author Max
 */
public class Cache {

    private static Map<String, Image> imageCache = new HashMap<>(); // Cache for images.
    private static Map<String, TrueTypeFont> fontCache = new HashMap<>(); // Cache for fonts.

    /**
     * Creates a new image object that loads an
     * image from the specified path, and adds
     * that image to cache.
     * @param source Path to an image.
     * @throws SlickException If there is an error loading the image.
     */
    public static Image loadImage(String source) throws SlickException {
        if(imageCache.containsKey(source)) {
            return imageCache.get(source);
        }
        else {
            Image loaded = new Image(source);
            imageCache.put(source, loaded);
            return loaded;
        }
    }

    /**
     * Adds an image to the cache.
     * @param source The path to the image.
     * @param image The image itself.
     */
    public static void addImage(String source, Image image) {
        imageCache.putIfAbsent(source, image);
    }

    /**
     * Checks whether the image cache contains an
     * image that was loaded from the specified source
     * path.
     * @param source The path to the image.
     * @return Whether an image exists from that source.
     */
    public static boolean containsImage(String source) {
        return imageCache.containsKey(source);
    }

    /**
     * Attempts to find an image from a specified
     * source within the image cache.
     * @param source The path to the source image file.
     * @return Image that was previously loaded; null if nothing is found.
     */
    public static Image getImage(String source) {
        return imageCache.get(source);
    }

    /**
     * Clears the image cache.
     */
    public static void clearImageCache() {
        imageCache.clear();
    }

    /**
     * Loads a font from a source file and registers
     * it to java, as well as to the cache.
     * @param source The path to the font file (.ttf).
     * @param name Name of the font.
     * @param style The font style.
     * @param size The font size.
     * @throws SlickException If there is an error loading the font.
     */
    public static TrueTypeFont loadFont(String source, String name, int style, float size) throws SlickException {
        if(fontCache.containsKey(name)) {
            return fontCache.get(name);
        }
        else {
            try {
                TrueTypeFont newFont = new TrueTypeFont(
                        Font.createFont(Font.TRUETYPE_FONT, new File(source)).deriveFont(style, size), true);
                fontCache.put(name, newFont);
                return newFont;
            }
            catch(IOException | FontFormatException e) {
                throw new SlickException(e.getMessage());
            }
        }
    }

    /**
     * Adds a font to the cache if it isn't already
     * added.
     * @param source Path to the font file (.ttf).
     * @throws SlickException If there's an error adding the font.
     */
    public static void addFont(String source, String name, int style, float size) throws SlickException {
        if(!fontCache.containsKey(name)) {
            try {
                fontCache.put(name, new TrueTypeFont(
                        Font.createFont(Font.TRUETYPE_FONT, new File(source)).deriveFont(style, size), true));
            }
            catch(IOException | FontFormatException e) {
                throw new SlickException(e.getMessage());
            }
        }
    }

    /**
     * Checks to see if a font is registered from
     * that specified path.
     * @param name Name of the font.
     * @return Whether the font is registered.
     */
    public static boolean containsFont(String name) {
        return fontCache.containsKey(name);
    }

    /**
     * Gets a registered font from the fontCache
     * by using the path of the font file.
     * @param name Name of the font.
     * @return The registered font.
     */
    public static TrueTypeFont getFont(String name) {
        return fontCache.get(name);
    }

    /**
     * Clears the font cache.
     */
    public static void clearFontCache() {
        fontCache.clear();
    }
}
