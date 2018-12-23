/**
 * OpenGL 2D texture abstraction
 *
 * @author Max
 * @since 1.0
 */

package com.magneticstudio.transience.devkit

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL30.*
import java.awt.image.DataBufferByte
import java.nio.ByteBuffer
import javax.imageio.ImageIO

// Thanks to Romain Guy for the idea:
//
//inline val Int.rgba_red get() = (this shr 24) and 0xFF
//inline val Int.rgba_green get() = (this shr 16) and 0xFF
//inline val Int.rgba_blue get() = (this shr 8) and 0xFF
//inline val Int.rgba_alpha get() = (this) and 0xFF
//
//inline val Int.abgr_red get() = (this) and 0xFF
//inline val Int.abgr_green get() = (this shr 8) and 0xFF
//inline val Int.abgr_blue get() = (this shr 16) and 0xFF
//inline val Int.abgr_alpha get() = (this shr 24) and 0xFF

inline val Int.argb_red_b   get(): Byte = ((this shr 16) and 0xFF).toByte()
inline val Int.argb_green_b get(): Byte = ((this shr 8)  and 0xFF).toByte()
inline val Int.argb_blue_b  get(): Byte = ((this)        and 0xFF).toByte()
inline val Int.argb_alpha_b get(): Byte = ((this shr 24) and 0xFF).toByte()

// NOTE(max): If needed bump and gloss maps will be added later.
// NOTE(max): The data is expected to be in BGRA / BGR format.
class Texture2 private constructor(
    width: Int,
    height: Int,
    data: ByteBuffer)
{
    private var glTextureId = 0

    var slot = 0
        set(newSlot) {
            field = GL_TEXTURE0 + newSlot
        }

    init {
        glTextureId = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, glTextureId)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RGBA,
                width,
                height,
                0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                data
        )
        glGenerateMipmap(GL_TEXTURE_2D)

        collectGLErrors().reportThemBy(WRITING_TO_LOGS and THROWING_EXCEPTION)
    }

    companion object Factory {

        private val TEXTURE_JAR_LOCATION = "textures"

        /**
         * Loads a texture from the specified file in the specified subdirectory
         * of the TEXTURE_JAR_LOCATION package in the jar file. If it cannot be found
         * a texture error is reported and an exception is possibly thrown.
         *
         * Java's ImageIO and BufferedImage is used to load the texture, so if you're messing
         * around with the code please note that the pixel format for the BufferedImage is ABGR,
         * which is why we have to reverse the bytes in the integers near the end of the function.
         *
         * The final result is a texture object containing an OpenGL handle for the texture.
         *
         * @param file The name of the file (for example "wood.jpg")
         * @param subdir If the file is contained in a subdirectory of src/textures/... then it can
         * be specified here.
         * @param flipVertically Whether to flip the image vertically when loading.
         */
        fun loadFromJar(file: String, subdir: String?, flipVertically: Boolean = true): Texture2 {
            val texIS = when (subdir) {
                null -> openJarResource("$TEXTURE_JAR_LOCATION/$file")
                else -> openJarResource("$TEXTURE_JAR_LOCATION/$subdir/$file")
            }

            if (texIS == null)
                reportTextureError("Failed to open JAR texture resource.", true)

            // NOTE(max): The buffered image stores the image data as ABGR
            val loaded = ImageIO.read(texIS)

            if (loaded == null)
                reportTextureError("javax.imageio.ImageIO.read(texIS) failed to read image (unrecognized)", true)

            val hasAlpha = loaded.colorModel.hasAlpha()

            // @HACK(max): Assumption: the number of components is 4 if it has alpha or 3 if it doesn't.
            // this may be a problem later on, just note that.
            val imgBuffer = BufferUtils.createByteBuffer(loaded.width * loaded.height * 4)

            for (y in
                if (flipVertically)
                    (loaded.height - 1 downTo 0)
                else
                    (0 until loaded.height))
            {
                for (x in 0 until loaded.width) {
                    // NOTE(max): According to the documentation BufferedImage.getRGB() returns
                    // an ARGB format integer.
                    val argb: Int = loaded.getRGB(x, y)
                    imgBuffer.put(argb.argb_red_b)
                    imgBuffer.put(argb.argb_green_b)
                    imgBuffer.put(argb.argb_blue_b)
                    imgBuffer.put(argb.argb_alpha_b)
                }
            }
            imgBuffer.flip()

            return Texture2(loaded.width, loaded.height, imgBuffer)
        }
    }

    fun bind() {
        glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, glTextureId)
    }

    fun unbind() {
        glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, 0)
    }
}