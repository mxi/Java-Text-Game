package com.magneticstudio.transience.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This interface simply defines a function that
 * may be implemented by any class that generates
 * an image object with special effects that have
 * need each pixel to be calculated.
 *
 * NOTE: THIS SHOULD NOT BE USED DURING RUNTIME RENDERING
 * AS THE CPU IS EXTREMELY SLOW COMPARED TO THE GPU
 *
 * @author Max
 */
public interface ImageGenerator {

    /**
     * Renders an effect onto an image.
     * @return The image with the rendered effect on it.
     */
    Image generate() throws SlickException;
}
