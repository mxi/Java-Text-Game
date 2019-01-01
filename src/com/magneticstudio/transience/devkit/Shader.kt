/**
 * OpenGL shader abstraction.
 *
 * @author Max
 * @since 1.1
 */

package com.magneticstudio.transience.devkit

import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER

private const val INFO_LOG_BUFFER_SIZE = 256

class Shader private constructor(
     vertexSource: String?,
     geometrySource: String?,
     fragmentSource: String?)
{
    var program: Int
        private set

    init {
        if (vertexSource == null || fragmentSource == null)
            reportGLError("Shader error: Vertex or Fragment shader(s) are/is null.", true)

        val vertex = compileShader(GL_VERTEX_SHADER, vertexSource as String, true)
        val fragment = compileShader(GL_FRAGMENT_SHADER, fragmentSource as String, true)

        if (vertex == null || fragment == null)
            reportGLError("Shader error: Cannot create shader with null Vertex of Fragment shader.", true)

        var geometry: Int? = null

        geometrySource?.let {
            geometry = compileShader(GL_GEOMETRY_SHADER, it, true)
        }

        program = glCreateProgram()
        glAttachShader(program, vertex as Int)
        glAttachShader(program, fragment as Int)

        geometry?.let {
            glAttachShader(program, geometry as Int)
        }

        glLinkProgram(program)

        val linkStatus = glGetProgrami(program, GL_LINK_STATUS)
        if (linkStatus == GL_FALSE) {
            val message: String = glGetProgramInfoLog(program, INFO_LOG_BUFFER_SIZE)
            reportGLError("Linking of shaders failed: $message", true)
            program = 0
        }

        glDeleteShader(vertex)
        glDeleteShader(fragment)
        geometry?.let {
            glDeleteShader(geometry as Int)
        }

        collectGLErrors().reportThemBy(WRITING_TO_LOGS and THROWING_EXCEPTION)
    }

    companion object Factory {

        // The directory of the shaders where the parent directory is src/
        val SHADER_JAR_LOCATION = "shaders"

        private fun translateShaderTypeToString(type: Int): String = when (type) {
            GL_VERTEX_SHADER -> "GL_VERTEX_SHADER"
            GL_GEOMETRY_SHADER -> "GL_GEOMETRY_SHADER"
            GL_FRAGMENT_SHADER -> "GL_FRAGMENT_SHADER"
            else -> "UNKNOWN_SHADER"
        }

        private fun compileShader(type: Int, source: String, allowThrowOnError: Boolean): Int? {
            val shader = glCreateShader(type)

            glShaderSource(shader, source)
            glCompileShader(shader)

            val compileStatus = glGetShaderi(shader, GL_COMPILE_STATUS)

            // Failed to compile:
            if (compileStatus == GL_FALSE) {
                val message: String = glGetShaderInfoLog(shader, INFO_LOG_BUFFER_SIZE)
                reportGLError("Compilation of ${translateShaderTypeToString(type)} failed: $message", allowThrowOnError)
                return null
            }

            return shader
        }

        /**
         * Loads a Shader from this jar file. It is expected that
         * the shaders are located within the SHADER_JAR_LOCATION
         * directory in the jar, and that all the shaders (vertex,
         * geometry, and fragment) are named vertex.glsl, geometry.gs,
         * and fragment.fs respectively.
         *
         * The geometry shader may be omitted.
         *
         * @param name The common name shared between the shaders and the name
         *             of the directory to find the shaders within.
         */
        fun loadFromJar(name: String): Shader {
            val vs = openJarResource("$SHADER_JAR_LOCATION/$name/vertex.glsl")
            val gs = openJarResource("$SHADER_JAR_LOCATION/$name/geometry.glsl")
            val fs = openJarResource("$SHADER_JAR_LOCATION/$name/fragment.glsl")

            // NOTE(max): readAll() extension function is null-safe so it doesn't matter if the
            // streams are null.
            return Shader(vs.readString(), gs.readString(), fs.readString())
        }
    }

    /**
     * Checks whether the program had compiled and linked successfully if for some reason
     * allowReportsToThrow is false and no errors were thrown.
     */
    fun isShaderValid(): Boolean = program != 0

    /**
     * Binds the shader to be able to be used by OpenGL.
     * NOTE(max): This does not check if the program was linked successfully.
     */
    fun bind(): Unit = glUseProgram(program)

    /**
     * Unbinds the current program (or any program for that matter)
     * from the GPU.
     */
    fun unbind(): Unit = glUseProgram(0)

    /**
     * Sets a texture's "slot" as the integer for the OpenGL sampler2D
     * type so that the sampler can use the texture.
     */
    inline fun uniformTex2D(name: String, tex2: Texture2)
            = glUniform1i(glGetUniformLocation(program, name), tex2.slot)

    /**
     * Sets a 4x4 matrix in this shader.
     */
    inline fun uniformMat4f(name: String, mat4: Mat4)
            = glUniformMatrix4(glGetUniformLocation(program, name), false, mat4.getShaderBuffer())
}