/**
 * OpenGL 2D texture abstraction
 *
 * @author Max
 * @since 1.0
 */

package com.magneticstudio.transience.devkit

import java.io.InputStream
import javax.imageio.ImageIO

// Thanks to Romain Guy for the idea:
inline val Int.red   get() = (this shr 24) and 0xFF
inline val Int.green get() = (this shr 16) and 0xFF
inline val Int.blue  get() = (this shr 8)  and 0xFF
inline val Int.alpha get() = (this)        and 0xFF

inline operator fun Int.component1() = this.red
inline operator fun Int.component2() = this.green
inline operator fun Int.component3() = this.blue
inline operator fun Int.component4() = this.alpha

// NOTE(max): If needed bump and gloss maps will be added later.
class Texture2 private constructor(
    width: Int,
    height: Int,
    texels: IntArray)
{
    private var glTextureId = 0

    init {

    }

    companion object Factory {

        private val TEXTURE_JAR_LOCATION = "textures"

        fun loadFromJar(file: String, subdir: String?): Texture2 {
            val texIS = when(subdir) {
                null -> openJarResource("$TEXTURE_JAR_LOCATION/$file")
                else -> openJarResource("$TEXTURE_JAR_LOCATION/$subdir/$file")
            }

            if (texIS == null)
                reportTextureError("Failed to open JAR texture resource.", true)

            ImageIO.read(texIS)
            return Texture2(0, 0, IntArray(2))
        }
    }
}