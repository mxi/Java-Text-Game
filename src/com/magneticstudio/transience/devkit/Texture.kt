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
inline val Int.rgba_red get() = (this shr 24) and 0xFF
inline val Int.rgba_green get() = (this shr 16) and 0xFF
inline val Int.rgba_blue get() = (this shr 8) and 0xFF
inline val Int.rgba_alpha get() = (this) and 0xFF

inline val Int.abgr_red get() = (this) and 0xFF
inline val Int.abgr_green get() = (this shr 8) and 0xFF
inline val Int.abgr_blue get() = (this shr 16) and 0xFF
inline val Int.abgr_alpha get() = (this shr 24) and 0xFF

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
         */
        fun loadFromJar(file: String, subdir: String?): Texture2 {
            val texIS = when (subdir) {
                null -> openJarResource("$TEXTURE_JAR_LOCATION/$file")
                else -> openJarResource("$TEXTURE_JAR_LOCATION/$subdir/$file")
            }

            if (texIS == null)
                reportTextureError("Failed to open JAR texture resource.", true)

            val componentsPerPixel = 4;

            // NOTE(max): The buffered image stores the image data as ABGR
            val loaded = ImageIO.read(texIS)
            val hasAlpha = loaded.alphaRaster != null
            val data = (loaded.raster.dataBuffer as DataBufferByte).data

            val imgBuffer = BufferUtils.createByteBuffer(loaded.width * loaded.height * componentsPerPixel)
            if (hasAlpha) {
                for (i in 0 until data.size step 4) {
                    val a = data[i]
                    val b = data[i + 1]
                    val g = data[i + 2]
                    val r = data[i + 3]
                    imgBuffer.put(r);
                    imgBuffer.put(g);
                    imgBuffer.put(b);
                    imgBuffer.put(a);
                }
            }
            else {
                for (i in 0 until data.size step 3) {
                    val b = data[i]
                    val g = data[i + 1]
                    val r = data[i + 2]
                    imgBuffer.put(r);
                    imgBuffer.put(g);
                    imgBuffer.put(b);
                    imgBuffer.put(0xFF.toByte());
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